package com.example.techspot.modules.orders.application.port;

import com.example.techspot.modules.notification.event.OrderCreatedEvent;

public interface OrderEventPublisher {

	void  publishOrderCreate(OrderCreatedEvent event);
}
