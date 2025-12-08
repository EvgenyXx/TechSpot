package com.example.techspot.modules.users.application.calculator;

import com.example.techspot.modules.api.order.OrderStatsProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserStatisticsCalculator {

	private final OrderStatsProvider orderStatsProvider;

	public Double calculateAverageOrdersPerUser(long totalUsers) {
		return orderStatsProvider.calculateAverageOrdersPerUser(totalUsers);
	}

	public Double calculateConversionRate(long totalUsers) {
		return orderStatsProvider.calculateConversionRate(totalUsers);
	}
}
