package com.example.techspot.modules.notification.dto;

import java.math.BigDecimal;

public record OrderItemDto(
		String title,
		int quantity,
		BigDecimal price,
		BigDecimal total
) {}
