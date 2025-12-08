package com.example.techspot.modules.users.application.dto.response;

public record UserPersonalStats(
		long totalOrders,
		long totalReviews,
		Double totalPriceOrders
) {}
