package com.example.TechSpot.service.admin;

import com.example.TechSpot.dto.user.UserDetailedStatistics;
import com.example.TechSpot.dto.user.UserStatistics;
import com.example.TechSpot.dto.user.UserDetailResponse;
import com.example.TechSpot.dto.user.UserResponse;
import com.example.TechSpot.entity.Role;
import com.example.TechSpot.entity.StatisticsPeriod;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.exception.UserAlreadyHasRoleException;
import com.example.TechSpot.mapping.UserMapper;
import com.example.TechSpot.repository.CartRepository;
import com.example.TechSpot.repository.UserRepository;
import com.example.TechSpot.service.order.OrderFinder;
import com.example.TechSpot.service.review.ReviewFinder;
import com.example.TechSpot.service.role.RoleFinder;
import com.example.TechSpot.service.user.query.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@Service
public class AdminUserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final UserFinder userFinder;
	private final OrderFinder orderFinder;
	private final ReviewFinder reviewFinder;
	private final RoleFinder roleFinder;
	private final CartRepository cartRepository;

	@Transactional(readOnly = true)
	public Page<UserResponse> getAllUsers(Pageable pageable) {
		log.info("Запрос на получение всех пользователей с пагинацией: page={}, size={}",
				pageable.getPageNumber(), pageable.getPageSize());

		Page<User> usersPage = userRepository.findAll(pageable);
		log.debug("Найдено {} пользователей на странице {}", usersPage.getNumberOfElements(), pageable.getPageNumber());

		Page<UserResponse> result = usersPage.map(user -> {
			log.trace("Маппинг пользователя {} в UserResponse", user.getId());
			return userMapper.toResponse(user);
		});

		log.info("Успешно возвращена страница с {} пользователями", result.getNumberOfElements());
		return result;
	}

	@Transactional
	public void toggleUserStatus(UUID userId, boolean isActive) {
		log.info("Запрос на изменение статуса пользователя: userId={}, isActive={}", userId, isActive);

		User user = userFinder.findById(userId);
		log.debug("Найден пользователь: id={}, текущий статус active={}", user.getId(), user.isActive());

		if (user.isActive() == isActive) {
			log.warn("Пользователь {} уже имеет требуемый статус active={}", userId, isActive);
			return;
		}

		if (isActive) {
			log.info("Разблокировка пользователя {}", user.getId());
			user.setActive(true);
		} else {
			log.warn("Блокировка пользователя {}", user.getId());
			user.setActive(false);
		}

		userRepository.save(user);
		log.info("Статус пользователя {} успешно изменен на active={}", user.getId(), isActive);
	}

	@Transactional(readOnly = true)
	public UserStatistics getUserStatistics() {
		log.info("Запрос на получение статистики пользователей");

		long totalUsers = userRepository.count();
		log.debug("Общее количество пользователей: {}", totalUsers);

		LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
		long newUsers = userRepository.countUsersCreatedAfter(thirtyDaysAgo);
		log.debug("Новых пользователей за 30 дней: {}", newUsers);

		long countActiveTrue = userRepository.countByIsActiveTrue();
		long countBlockedUsers = userRepository.countByIsActiveFalse();
		log.debug("Активных пользователей: {}, заблокированных: {}", countActiveTrue, countBlockedUsers);

		UserStatistics statistics = new UserStatistics(
				totalUsers,
				newUsers,
				countActiveTrue,
				countBlockedUsers,
				LocalDateTime.now()
		);

		log.info("Статистика пользователей успешно сгенерирована: total={}, newLast30d={}, active={}, blocked={}",
				totalUsers, newUsers, countActiveTrue, countBlockedUsers);

		return statistics;
	}


	// 1. Получить детальную информацию о пользователе
	public UserDetailResponse getUserDetail(UUID userId) {
		// TODO: Найти пользователя по ID
		// TODO: Получить дополнительную статистику (кол-во заказов, отзывов и т.д.)
		// TODO: Собрать всю информацию в UserDetailResponse
		User user = userFinder.findById(userId);
		long totalOrders = orderFinder.totalOrdersForUser(userId);
		long totalReviews = reviewFinder.countReviewsForUser(userId);
		Set<String> roles = user.getRoles().stream()
				.map(Role::getName)
				.collect(Collectors.toSet());
		Double totalPriceOrders = orderFinder.sumTotalAmountByUserId(userId);

		return new UserDetailResponse(
				userId, user.getFirstname(),
				user.getLastname(), user.getEmail(),
				user.getPhoneNumber(), user.isActive(),
				roles,user.getCreatedAt(),null,
				totalOrders,totalReviews, totalPriceOrders

		);



	}

//	// 2. Поиск пользователей с фильтрами
//	public Page<UserResponse> searchUsers(UserSearchRequest searchRequest, Pageable pageable) {
//		// TODO: Построить спецификацию на основе searchRequest
//		// TODO: Выполнить поиск с пагинацией
//		// TODO: Вернуть страницу с пользователями
//	}
//
//	// 3. Массовое изменение статуса пользователей
//	public void bulkUpdateUserStatus(List<UUID> userIds, boolean isActive) {
//		// TODO: Найти всех пользователей по списку ID
//		// TODO: Обновить статус для каждого пользователя
//		// TODO: Сохранить всех пользователей
//	}

	// 4. Получить расширенную статистику
	public UserDetailedStatistics getDetailedStatistics(StatisticsPeriod period) {

		LocalDateTime startDate =calculateStartDate(period);

		Long totalUsers = userRepository.count();
		Long newUsersInPeriod = userRepository.countActiveUsersAfter(startDate);
		Long activeUsersInPeriod = userRepository.countActiveUsersAfter(startDate);


		return new UserDetailedStatistics(
				totalUsers,
				newUsersInPeriod,
				activeUsersInPeriod,
				getRegistrationsByDate(startDate),
				getStat(),
				calculateConversionRate(),
				calculateAverageOrdersPerUser(),
				null,
				getUsersByRole(),
				null,
				period
		);
	}

	private Map<String,Long>getStat(){
		return cartRepository.getStat()
				.stream()
				.collect(Collectors.toMap(
						objects -> objects[0].toString(),
						o -> (Long) o[1]
				));
	}

	private Map<String,Long>getRegistrationsByDate(LocalDateTime dateTime){

		return userRepository.countRegistrationsByDay(dateTime)
				.stream()
				.collect(Collectors.toMap(
						objects -> objects[0].toString(),
						o -> (Long) o[1]
				));
	}

	private Map<String, Long> getUsersByRole() {
		return userRepository.countUsersByRole()
				.stream()
				.collect(Collectors.toMap(
						arr -> (String) arr[0],   // role name
						arr -> (Long) arr[1]      // count
				));
	}



	private Double calculateAverageOrdersPerUser() {
		long totalUsers = userRepository.count();
		Long totalOrders = orderFinder.countOrders();

		return (double) totalOrders / totalUsers;
	}

	private Double calculateConversionRate() {
		long totalUsers = userRepository.count();
		if (totalUsers == 0) return 0.0;

		Long usersWithOrders = orderFinder.countUsersWithAtLeastOneOrder();
		return (double) usersWithOrders / totalUsers * 100;
	}

	private LocalDateTime calculateStartDate(StatisticsPeriod period) {
		return switch (period) {
			case LAST_YEAR -> LocalDateTime.now().minusYears(1);
			case LAST_7_DAYS -> LocalDateTime.now().minusDays(7);
			case LAST_30_DAYS -> LocalDateTime.now().minusDays(30);
			case LAST_90_DAYS -> LocalDateTime.now().minusDays(90);
		};

	}

	@Transactional
	public void updateUserRole(UUID userId, String newRole) {
		log.info("Adding role to user: userId={}, role={}", userId, newRole);

		User user = userFinder.findById(userId);
		Role role = roleFinder.getUserRoleByName(newRole);

		if (user.getRoles().contains(role)) {
			log.warn("User already has role: userId={}, role={}", userId, newRole);
			throw new UserAlreadyHasRoleException();
		}

		user.getRoles().add(role);

		log.info("Role added successfully: userId={}, role={}", userId, newRole);
	}

}