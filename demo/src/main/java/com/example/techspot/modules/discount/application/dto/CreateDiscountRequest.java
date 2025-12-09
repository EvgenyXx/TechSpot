package com.example.techspot.modules.discount.application.dto;

import com.example.techspot.modules.discount.domain.entity.DiscountType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Запрос на создание скидки")
public record CreateDiscountRequest(

		@NotNull(message = "Discount type is required")
		@Schema(
				description = "Тип скидки",
				requiredMode = Schema.RequiredMode.REQUIRED,
				example = "PERCENTAGE",
				allowableValues = {"PERCENTAGE", "FIXED_AMOUNT", "BUY_X_GET_Y"}
		)
		DiscountType type,

		@Positive(message = "Percentage must be greater than 0")
		@Max(value = 100, message = "Percentage cannot exceed 100")
		@Schema(
				description = "Процент скидки (используется для PERCENTAGE типа)",
				minimum = "0.01",
				maximum = "100.00",
				example = "15.50"
		)
		BigDecimal percentage,

		@Positive(message = "Fixed amount must be greater than 0")
		@Schema(
				description = "Фиксированная сумма скидки (используется для FIXED_AMOUNT типа)",
				minimum = "0.01",
				example = "1000.00"
		)
		BigDecimal fixedAmount,

		@FutureOrPresent(message = "Start date must be now or in the future")
		@Schema(
				description = "Дата и время начала действия скидки",
				requiredMode = Schema.RequiredMode.REQUIRED,
				example = "2024-12-01T10:00:00"
		)
		LocalDateTime startsAt,

		@Future(message = "End date must be in the future")
		@Schema(
				description = "Дата и время окончания действия скидки",
				requiredMode = Schema.RequiredMode.REQUIRED,
				example = "2024-12-31T23:59:59"
		)
		LocalDateTime endsAt,

		@Positive(message = "Product ID must be positive")
		@Schema(
				description = "ID товара для скидки (если скидка на конкретный товар)",
				minimum = "1",
				example = "123"
		)
		Long productId,

		@Positive(message = "Category ID must be positive")
		@Schema(
				description = "ID категории для скидки (если скидка на категорию)",
				minimum = "1",
				example = "456"
		)
		Long categoryId,

		@Positive(message = "Количество должно быть положительным")
		@Schema(
				description = "Минимальное количество товара для активации скидки",
				minimum = "1",
				example = "3"
		)
		Integer minQuantity,

		@Schema(
				description = "Активна ли скидка при создании",
				defaultValue = "true",
				example = "true"
		)
		boolean active
) {}