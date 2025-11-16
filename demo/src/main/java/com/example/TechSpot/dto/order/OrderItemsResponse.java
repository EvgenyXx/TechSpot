package com.example.TechSpot.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Информация о товаре в заказе")
public record OrderItemsResponse(
		@Schema(
				description = "Название товара",
				example = "iPhone 15 Pro"
		)
		String productName,

		@Schema(
				description = "Цена за единицу товара",
				example = "999.99"
		)
		BigDecimal itemPrice,

		@Schema(
				description = "Количество товара",
				example = "2"
		)
		Integer quantity
) {
}