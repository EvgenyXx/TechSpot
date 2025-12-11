package com.example.techspot.modules.orders.infrastructure.adapter;

import com.example.techspot.core.config.KafkaProperties;
import com.example.techspot.modules.notification.event.OrderCreatedEvent;
import com.example.techspot.modules.orders.application.port.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaOrderEventPublisher implements OrderEventPublisher {
	private final KafkaTemplate<String,OrderCreatedEvent>kafkaTemplate;
	private final KafkaProperties kafkaProperties;

	@Override
	public void publishOrderCreate(OrderCreatedEvent event) {
		String topicName = kafkaProperties.getTopics().getOrderCreated();
		kafkaTemplate.send(topicName,event);
	}
}
