package com.example.TechSpot.modules.cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Запрос на добавление товара в корзину")
public record AddToCartRequest(
		@Schema(description = "ID товара", example = "123")
		@NotNull(message = "Product ID cannot be null")
		Long productId,

		@Schema(description = "Количество товара", example = "2")
		@Positive(message = "Quantity must be positive")
		int quantity
) {
}