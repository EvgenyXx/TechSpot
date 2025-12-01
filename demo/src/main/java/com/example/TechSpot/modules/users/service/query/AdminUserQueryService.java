package com.example.TechSpot.modules.users.service.query;

import com.example.TechSpot.modules.api.order.UserOrderStatsProvider;
import com.example.TechSpot.modules.api.review.ReviewStatsProvider;
import com.example.TechSpot.modules.users.dto.response.UserDetailResponse;
import com.example.TechSpot.modules.users.dto.response.UserResponse;
import com.example.TechSpot.modules.users.entity.Role;
import com.example.TechSpot.modules.users.entity.User;
import com.example.TechSpot.modules.users.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class AdminUserQueryService {

	private final UserMapper userMapper;
	private final UserOrderStatsProvider userOrderStatsProvider;
	private final ReviewStatsProvider reviewStatsProvider;
	private final UserQueryService userQueryService;

	@Transactional(readOnly = true)
	public Page<UserResponse> getAllUsers(Pageable pageable) {
		log.info("Запрос списка пользователей: page={}, size={}",
				pageable.getPageNumber(), pageable.getPageSize());

		Page<User> usersPage = userQueryService.findAllUsers(pageable);
		log.debug("Найдено {} пользователей", usersPage.getNumberOfElements());

		Page<UserResponse> result = usersPage.map(userMapper::toResponse);

		log.info("Успешно возвращено {} пользователей", result.getNumberOfElements());
		return result;
	}

	@Transactional(readOnly = true)
	public UserDetailResponse getUserDetail(UUID userId) {
		log.info("Запрос детальной информации пользователя: {}", userId);

		User user = userQueryService.findById(userId);
		log.debug("Пользователь найден: {} {}", user.getFirstname(), user.getLastname());

		long totalOrders = userOrderStatsProvider.countOrdersForUser(userId);
		long totalReviews = reviewStatsProvider.countReviewsForUser(userId);
		Double totalPriceOrders = userOrderStatsProvider.sumTotalAmountByUserId(userId);

		Set<String> roles = user.getRoles().stream()
				.map(Role::getName)
				.collect(Collectors.toSet());

		log.debug("Статистика пользователя: orders={}, reviews={}, totalSpent={}",
				totalOrders, totalReviews, totalPriceOrders);

		UserDetailResponse response = new UserDetailResponse(
				userId, user.getFirstname(),
				user.getLastname(), user.getEmail(),
				user.getPhoneNumber(), user.isActive(),
				roles, user.getCreatedAt(), null,
				totalOrders, totalReviews, totalPriceOrders
		);

		log.info("Детальная информация пользователя {} успешно собрана", userId);
		return response;
	}
}