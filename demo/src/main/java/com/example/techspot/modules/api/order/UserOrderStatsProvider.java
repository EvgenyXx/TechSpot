package com.example.techspot.modules.api.order;

import java.util.UUID;

public interface UserOrderStatsProvider {

	Long countOrdersForUser(UUID userId);
	Double sumTotalAmountByUserId(UUID userId);
}
