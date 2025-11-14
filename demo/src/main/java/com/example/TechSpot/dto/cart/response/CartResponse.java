package com.example.TechSpot.dto.cart.response;

import java.math.BigDecimal;
import java.util.Set;

public record CartResponse(

		Set<CartItemResponse>cartItemResponses ,
		BigDecimal totalPrice

) {
}
