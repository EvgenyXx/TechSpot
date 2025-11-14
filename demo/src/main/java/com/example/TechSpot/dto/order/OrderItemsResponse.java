package com.example.TechSpot.dto.order;


import java.math.BigDecimal;

public record OrderItemsResponse(


		String productName,
		BigDecimal itemPrice,

		Integer quantity
) {
}
