package com.example.techspot.modules.notification.event;

import com.example.techspot.modules.notification.dto.OrderItemDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCreatedEvent(
		Long orderId,
		String userEmail,
		BigDecimal totalPrice,
		LocalDateTime orderDate,
		Long itemsCount,
		List<OrderItemDto> items

) implements NotificationEvent{
	@Override
	public String getEmail() {
		return this.userEmail;
	}

	@Override
	public String getType() {
		return "ORDER_CREATED";
	}
}
