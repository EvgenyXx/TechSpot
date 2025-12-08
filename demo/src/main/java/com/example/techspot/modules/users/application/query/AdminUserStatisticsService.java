package com.example.techspot.modules.users.application.query;

import com.example.techspot.modules.api.cart.CartStatsProvider;
import com.example.techspot.modules.users.application.calculator.AdminUserStatisticsCalculator;
import com.example.techspot.modules.users.application.dto.response.UserDetailedStatistics;
import com.example.techspot.modules.users.application.dto.response.UserStatistics;
import com.example.techspot.modules.users.application.factory.AdminUserDTOFactory;
import com.example.techspot.modules.users.domain.entity.StatisticsPeriod;
import com.example.techspot.modules.users.domain.service.StatisticsPeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminUserStatisticsService {

	private final UserStatisticsService userStatisticsService;
	private final CartStatsProvider cartStatsProvider;
	private final AdminUserStatisticsCalculator calculator;
	private final StatisticsPeriodService periodService;
	private final AdminUserDTOFactory dtoFactory;

	@Transactional(readOnly = true)
	public UserDetailedStatistics getDetailedStatistics(StatisticsPeriod period) {

		LocalDateTime start = periodService.resolveStartDate(period);

		Long totalUsers = userStatisticsService.countUsers();
		Long newUsers = userStatisticsService.countUsersCreatedAfter(start);
		Long activeUsers = userStatisticsService.countActiveUsersAfter(start);

		Double avgOrders = calculator.calculateAverageOrdersPerUser(totalUsers);
		Double conversion = calculator.calculateConversionRate(totalUsers);

		return dtoFactory.buildDetailedStatistics(
				totalUsers,
				newUsers,
				activeUsers,
				userStatisticsService.getRegistrationsByDate(start),
				cartStatsProvider.getCartStatistics(),
				conversion,
				avgOrders,
				userStatisticsService.getUsersByRole(),
				period
		);
	}

	@Transactional(readOnly = true)
	public UserStatistics getUserStatistics() {

		long totalUsers = userStatisticsService.countUsers();
		long newUsers = userStatisticsService.countUsersCreatedAfter(LocalDateTime.now().minusDays(30));
		long activeUsers = userStatisticsService.countByIsActiveTrue();
		long blockedUsers = userStatisticsService.countByIsActiveFalse();

		return dtoFactory.buildUserStatistics(
				totalUsers,
				newUsers,
				activeUsers,
				blockedUsers
		);
	}
}
