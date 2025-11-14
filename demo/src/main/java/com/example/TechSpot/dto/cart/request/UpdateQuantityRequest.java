package com.example.TechSpot.dto.cart.request;

import java.util.UUID;
//todo добавить валидацию

public record UpdateQuantityRequest(
		UUID userId, Long cartItemId, int newQuantity
) {
}
