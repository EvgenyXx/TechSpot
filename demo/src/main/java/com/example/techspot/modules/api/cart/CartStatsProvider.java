package com.example.techspot.modules.api.cart;

import java.util.Map;

public interface CartStatsProvider {

	Map<String, Long> getCartStatistics();
}
