package com.example.TechSpot.modules.discount;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateDiscountRequest(
		DiscountType type,
		BigDecimal percentage,
		BigDecimal fixedAmount,
		LocalDateTime startsAt,
		LocalDateTime endsAt,
		Long productId,
		Long categoryId,
		boolean active
) {}