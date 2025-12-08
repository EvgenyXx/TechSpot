package com.example.techspot.modules.discount.application.dto;

import com.example.techspot.modules.discount.domain.entity.DiscountType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//todo swagger
public record CreateDiscountRequest(

		@NotNull(message = "Discount type is required")
		DiscountType type,

		@Positive(message = "Percentage must be greater than 0")
		@Max(value = 100, message = "Percentage cannot exceed 100")
		BigDecimal percentage,

		@Positive(message = "Fixed amount must be greater than 0")
		BigDecimal fixedAmount,

		@FutureOrPresent(message = "Start date must be now or in the future")
		LocalDateTime startsAt,

		@Future(message = "End date must be in the future")
		LocalDateTime endsAt,

		@Positive(message = "Product ID must be positive")
		Long productId,

		@Positive(message = "Category ID must be positive")
		Long categoryId,

		@Positive(message = "Количество должно быть положительным")
		Integer minQuantity,

		boolean active
) {}
