package com.example.techspot.modules.orders.application.command;

import com.example.techspot.modules.cart.domain.entity.Cart;
import com.example.techspot.modules.notification.event.OrderCreatedEvent;
import com.example.techspot.modules.orders.application.factory.OrderEventFactory;
import com.example.techspot.modules.orders.application.port.OrderEventPublisher;
import com.example.techspot.modules.orders.domain.entity.Order;
import com.example.techspot.modules.orders.application.dto.OrderResponse;
import com.example.techspot.modules.orders.application.factory.OrderResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;



@Service
@Log4j2
@RequiredArgsConstructor
public class CommandOrderService {

	private final OrderCartValidationAction cartValidation;
	private final OrderStockReservationAction stockReservation;
	private final OrderCreateAction orderCreateAction;
	private final OrderCartCleanupAction cartCleanupAction;
	private final OrderResponseFactory responseFactory;
//	private final ApplicationEventPublisher eventPublisher;
	private final OrderEventFactory orderEventFactory;
	private final OrderEventPublisher orderEventPublisher;



	@Transactional
	public OrderResponse checkout(UUID userId) {

		log.info("Checkout user {}", userId);

		Cart cart = cartValidation.validateCart(userId);

		stockReservation.reserveStock(cart);

		Order order = orderCreateAction.create(cart);

		OrderCreatedEvent event = orderEventFactory.buildOrderCreatedEvent(order);
//		eventPublisher.publishEvent(event);
		orderEventPublisher.publishOrderCreate(event);
		cartCleanupAction.clear(userId);

		return responseFactory.build(order);
	}
}