package com.example.TechSpot.dto.order;

import com.example.TechSpot.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.Set;

@Schema(description = "Ответ с информацией о заказе")
public record OrderResponse(
		@Schema(
				description = "ID заказа",
				example = "123"
		)
		Long id,

		@Schema(
				description = "Номер заказа",
				example = "ORD-2024-00123"
		)
		String orderNumber,

		@Schema(
				description = "Общая стоимость заказа",
				example = "1999.98"
		)
		BigDecimal totalPrice,

		@Schema(
				description = "Статус заказа",
				example = "PROCESSING"
		)
		OrderStatus orderStatus,

		@Schema(
				description = "Список товаров в заказе",
				example = """
            [
              {
                "productId": 456,
                "productName": "iPhone 15 Pro",
                "itemPrice": 999.99,
                "quantity": 2,
                "totalPrice": 1999.98
              }
            ]
            """
		)
		Set<OrderItemsResponse> orderItems
) {
}