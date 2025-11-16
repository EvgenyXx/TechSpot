package com.example.TechSpot.dto.cart.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Запрос на удаление товара из корзины")
public record RemoveFromCartRequest(
		@Schema(
				description = "ID позиции в корзине",
				example = "123"
		)
		@NotNull(message = "Cart item ID cannot be null")
		Long cartItemId
) {
}