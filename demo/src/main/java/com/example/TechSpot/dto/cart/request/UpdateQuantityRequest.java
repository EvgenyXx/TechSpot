package com.example.TechSpot.dto.cart.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Запрос на обновление количества товара в корзине")
public record UpdateQuantityRequest(
		@Schema(
				description = "ID позиции в корзине",
				example = "123"
		)
		@NotNull(message = "Cart item ID cannot be null")
		Long cartItemId,

		@Schema(
				description = "Новое количество товара",
				example = "5",
				minimum = "1"
		)
		@Positive(message = "Quantity must be positive number")
		int newQuantity
) {
}