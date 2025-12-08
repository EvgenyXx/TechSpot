package com.example.techspot.modules.users.application.service;

import com.example.techspot.modules.api.order.UserOrderStatsProvider;
import com.example.techspot.modules.api.review.ReviewStatsProvider;
import com.example.techspot.modules.users.application.dto.response.UserPersonalStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPersonalStatisticsService {

	private final UserOrderStatsProvider userOrderStatsProvider;
	private final ReviewStatsProvider reviewStatsProvider;

	public UserPersonalStats getPersonalStats(UUID userId) {
		long totalOrders = userOrderStatsProvider.countOrdersForUser(userId);
		long totalReviews = reviewStatsProvider.countReviewsForUser(userId);
		Double totalPriceOrders = userOrderStatsProvider.sumTotalAmountByUserId(userId);

		return new UserPersonalStats(totalOrders, totalReviews, totalPriceOrders);
	}
}
