package com.example.TechSpot.service.order;

import com.example.TechSpot.dto.order.OrderResponse;
import com.example.TechSpot.entity.*;
import com.example.TechSpot.exception.order.OrderNotFoundException;
import com.example.TechSpot.exception.cart.EmptyCartException;
import com.example.TechSpot.mapping.OrderMapper;
import com.example.TechSpot.repository.OrderRepository;
import com.example.TechSpot.service.cart.CartInitializationService;
import com.example.TechSpot.service.cart.CommandCartService;
import com.example.TechSpot.service.user.query.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class CommandOrderService {

	private final OrderRepository orderRepository;
	private final UserFinder userFinder;
	private final CommandCartService commandCartService;
	private final OrderMapper orderMapper;
	private final OrderCreationService orderCreationService;
	private final CartInitializationService cartInitializationService;

	@Transactional
	public OrderResponse checkout(UUID userId) {
		log.info("Начало оформления заказа для пользователя: {}", userId);

		User user = userFinder.findById(userId);
		Cart cart = cartInitializationService.ensureUserHasCart(user);

		if (cart == null || cart.getCartItems().isEmpty()) {
			log.warn("Попытка оформления заказа с пустой корзиной для пользователя: {}", userId);
			throw new EmptyCartException();
		}

		log.info("Корзина пользователя {} содержит {} товаров", userId, cart.getCartItems().size());
		orderCreationService.updateProductQuantities(cart);

		Order order = orderCreationService.createOrderFromCart(user, cart);
		Order savedOrder = orderRepository.save(order);
		commandCartService.clearCart(userId);

		log.info("Заказ успешно оформлен. Номер заказа: {}, ID: {}", savedOrder.getOrderNumber(), savedOrder.getId());
		return orderMapper.toOrderResponse(savedOrder);
	}



}