package com.example.techspot.modules.notification.service;

import com.example.techspot.modules.notification.event.NotificationEvent;
import com.example.techspot.modules.notification.event.OrderCreatedEvent;
import com.example.techspot.modules.notification.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderCreatedNotificationService implements NotificationStrategy {

	private final EmailSender emailSender;

	@Override
	public boolean support(NotificationEvent notificationEvent) {
		return notificationEvent instanceof OrderCreatedEvent;
	}

	@Override
	public void send(NotificationEvent notificationEvent) {
		OrderCreatedEvent event = (OrderCreatedEvent) notificationEvent;

		Map<String, Object> vars = new HashMap<>();
		vars.put("orderId",event.orderId());
		vars.put("orderDate",event.orderDate());
		vars.put("totalPrice",event.totalPrice());
		vars.put("itemsCount",event.itemsCount());
		vars.put("items",event.items());

		emailSender.sendEmail(
				event.userEmail(),
				"Оформление заказа",
				"emails/order-created",
				vars
				);

	}
}
