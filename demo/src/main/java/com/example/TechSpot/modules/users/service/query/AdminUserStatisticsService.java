package com.example.TechSpot.modules.users.service.query;

import com.example.TechSpot.modules.api.cart.CartStatsProvider;
import com.example.TechSpot.modules.api.order.OrderStatsProvider;
import com.example.TechSpot.modules.users.dto.response.UserDetailedStatistics;
import com.example.TechSpot.modules.users.dto.response.UserStatistics;
import com.example.TechSpot.modules.users.entity.StatisticsPeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Log4j2
@RequiredArgsConstructor
public class AdminUserStatisticsService {

	private final CartStatsProvider cartStatsProvider;
	private final OrderStatsProvider orderStatisticsProvider;
	private final UserStatisticsService userStatisticsService;

	@Transactional(readOnly = true)
	public UserDetailedStatistics getDetailedStatistics(StatisticsPeriod period) {
		log.info("Запрос расширенной статистики за период: {}", period);

		LocalDateTime startDate = calculateStartDate(period);
		log.debug("Начальная дата для статистики: {}", startDate);

		Long totalUsers = userStatisticsService.countUsers();
		Long newUsersInPeriod = userStatisticsService.activeUsersInPeriod(startDate);
		Long activeUsersInPeriod = userStatisticsService.countActiveUsersAfter(startDate);

		log.debug("Статистика собрана: totalUsers={}, newUsers={}, activeUsers={}",
				totalUsers, newUsersInPeriod, activeUsersInPeriod);

		UserDetailedStatistics statistics = new UserDetailedStatistics(
				totalUsers,
				newUsersInPeriod,
				activeUsersInPeriod,
				userStatisticsService.getRegistrationsByDate(startDate),
				cartStatsProvider.getCartStatistics(),
				calculateConversionRate(),
				calculateAverageOrdersPerUser(),
				null,
				userStatisticsService.getUsersByRole(),
				null,
				period
		);

		log.info("Расширенная статистика успешно сгенерирована для периода: {}", period);
		return statistics;
	}

	@Transactional(readOnly = true)
	public UserStatistics getUserStatistics() {
		log.info("Запрос базовой статистики пользователей");

		long totalUsers = userStatisticsService.countUsers();

		LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);

		long newUsers = userStatisticsService.countUsersCreatedAfter(thirtyDaysAgo);
		long countActiveTrue = userStatisticsService.countByIsActiveTrue();
		long countBlockedUsers = userStatisticsService.countByIsActiveFalse();

		log.debug("Собраны метрики: total={}, newLast30d={}, active={}, blocked={}",
				totalUsers, newUsers, countActiveTrue, countBlockedUsers);

		UserStatistics statistics = new UserStatistics(
				totalUsers,
				newUsers,
				countActiveTrue,
				countBlockedUsers,
				LocalDateTime.now()
		);

		log.info("Базовая статистика пользователей успешно сгенерирована");
		return statistics;
	}




	private Double calculateAverageOrdersPerUser() {
		log.trace("Расчет среднего количества заказов на пользователя");
		long totalUsers = userStatisticsService.countUsers();

		// ✅ Передаем totalUsers в метод провайдера
		Double result = orderStatisticsProvider.calculateAverageOrdersPerUser(totalUsers);

		log.debug("Среднее количество заказов на пользователя: {}", result);
		return result;
	}

	private Double calculateConversionRate() {
		log.trace("Расчет конверсии пользователей");
		long totalUsers = userStatisticsService.countUsers();

		// ✅ Передаем totalUsers в метод провайдера
		Double result = orderStatisticsProvider.calculateConversionRate(totalUsers);

		log.debug("Конверсия пользователей: {}%", String.format("%.2f", result));
		return result;
	}

	private LocalDateTime calculateStartDate(StatisticsPeriod period) {
		log.trace("Вычисление начальной даты для периода: {}", period);
		return switch (period) {
			case LAST_YEAR -> LocalDateTime.now().minusYears(1);
			case LAST_7_DAYS -> LocalDateTime.now().minusDays(7);
			case LAST_30_DAYS -> LocalDateTime.now().minusDays(30);
			case LAST_90_DAYS -> LocalDateTime.now().minusDays(90);
		};
	}
}