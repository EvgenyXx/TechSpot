package com.example.TechSpot.dto.cart.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.Set;

@Schema(description = "Ответ с информацией о корзине пользователя")
public record CartResponse(
		@Schema(
				description = "Список товаров в корзине",
				example = """
            [
              {
                "cartItemId": 123,
                "productId": 456,
                "productName": "iPhone 15 Pro",
                "unitPrice": 999.99,
                "quantity": 2,
                "totalPrice": 1999.98
              }
            ]
            """
		)
		Set<CartItemResponse> cartItemResponses,

		@Schema(
				description = "Общая стоимость корзины",
				example = "1999.98"
		)
		BigDecimal totalPrice
) {
}