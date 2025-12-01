package com.example.TechSpot.modules.api.order;

public interface OrderStatsProvider {

	Double calculateAverageOrdersPerUser(Long totalUsers);
	Double calculateConversionRate(Long totalUsers);

}
