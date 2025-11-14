package com.example.TechSpot.dto.order;

import com.example.TechSpot.entity.OrderItems;
import com.example.TechSpot.entity.OrderStatus;
import com.example.TechSpot.entity.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

public record OrderResponse(

		Long id,

		String orderNumber,


		BigDecimal totalPrice,


		OrderStatus orderStatus,

		Set<OrderItemsResponse> orderItems
) {
}
