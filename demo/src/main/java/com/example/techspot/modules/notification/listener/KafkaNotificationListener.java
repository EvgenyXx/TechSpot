package com.example.techspot.modules.notification.listener;


import com.example.techspot.modules.notification.event.OrderCreatedEvent;
import com.example.techspot.modules.notification.service.NotificationDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaNotificationListener {

	private final NotificationDispatcher dispatcher;

	@KafkaListener(
			topics = "${app.kafka.topics.order-created}",
			groupId = "notifications"
	)
	public void onMessage(OrderCreatedEvent event) {
		dispatcher.handler(event);
	}
}