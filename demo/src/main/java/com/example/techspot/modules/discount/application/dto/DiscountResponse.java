package com.example.techspot.modules.discount.application.dto;

import com.example.techspot.modules.discount.domain.entity.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
//todo swagger

public record DiscountResponse(
		Long id,
		DiscountType type,
		BigDecimal percentage,
		BigDecimal fixedAmount,
		LocalDateTime startsAt,
		LocalDateTime endsAt,
		Long productId,
		Long categoryId,
		boolean active,
		Integer maxminQuantity
) {}
