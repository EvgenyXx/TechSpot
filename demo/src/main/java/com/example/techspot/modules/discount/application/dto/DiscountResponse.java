package com.example.techspot.modules.discount.application.dto;

import com.example.techspot.modules.discount.domain.entity.DiscountType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Ответ с информацией о скидке")
public record DiscountResponse(

		@Schema(
				description = "Уникальный идентификатор скидки",
				example = "1"
		)
		Long id,

		@Schema(
				description = "Тип скидки",
				requiredMode = Schema.RequiredMode.REQUIRED,
				example = "PERCENTAGE",
				allowableValues = {"PERCENTAGE", "FIXED_AMOUNT", "BUY_X_GET_Y"}
		)
		DiscountType type,

		@Schema(
				description = "Процент скидки (для PERCENTAGE типа)",
				example = "15.50",
				nullable = true
		)
		BigDecimal percentage,

		@Schema(
				description = "Фиксированная сумма скидки (для FIXED_AMOUNT типа)",
				example = "1000.00",
				nullable = true
		)
		BigDecimal fixedAmount,

		@Schema(
				description = "Дата и время начала действия скидки",
				requiredMode = Schema.RequiredMode.REQUIRED,
				example = "2024-12-01T10:00:00"
		)
		LocalDateTime startsAt,

		@Schema(
				description = "Дата и время окончания действия скидки",
				requiredMode = Schema.RequiredMode.REQUIRED,
				example = "2024-12-31T23:59:59"
		)
		LocalDateTime endsAt,

		@Schema(
				description = "ID товара, к которому применяется скидка",
				example = "123",
				nullable = true
		)
		Long productId,

		@Schema(
				description = "ID категории, к которой применяется скидка",
				example = "456",
				nullable = true
		)
		Long categoryId,

		@Schema(
				description = "Статус активности скидки",
				example = "true"
		)
		boolean active,

		@Schema(
				description = "Минимальное количество товара для активации скидки",
				example = "3",
				nullable = true
		)
		Integer maxminQuantity  // Примечание: возможно, опечатка в названии поля - должно быть minQuantity?
) {}