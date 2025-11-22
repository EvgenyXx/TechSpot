package com.example.TechSpot.dto.cart.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Элемент корзины с информацией о товаре")
public record CartItemResponse(
		@Schema(
				description = "Название товара",
				example = "iPhone 15 Pro"
		)
		String productName,

		BigDecimal price
) {
}