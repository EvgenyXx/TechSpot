package com.example.techspot.modules.orders.application.factory;


import com.example.techspot.modules.notification.dto.OrderItemDto;
import com.example.techspot.modules.notification.event.OrderCreatedEvent;
import com.example.techspot.modules.orders.domain.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderEventFactory {

	public OrderCreatedEvent buildOrderCreatedEvent(Order order) {
		List<OrderItemDto> items = order.getOrderItems()
				.stream()
				.map(it -> new OrderItemDto(
						it.getProductName(),
						it.getQuantity(),
						it.getItemPrice(),
						it.getTotalPrice()
				))
				.toList();

		return new OrderCreatedEvent(
				order.getId(),
				order.getUser().getEmail(),
				order.getTotalPrice(),
				order.getCreatedAt(),
				(long) order.getOrderItems().size(),
				items
		);
	}
}
