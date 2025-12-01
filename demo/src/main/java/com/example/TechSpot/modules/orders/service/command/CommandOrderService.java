package com.example.TechSpot.modules.orders.service.command;

import com.example.TechSpot.modules.api.cart.CartOrderProvider;
import com.example.TechSpot.modules.api.user.UserRepositoryProvider;
import com.example.TechSpot.modules.cart.entity.Cart;
import com.example.TechSpot.modules.orders.entity.Order;
import com.example.TechSpot.modules.orders.mapper.OrderMapper;
import com.example.TechSpot.modules.orders.repository.OrderRepository;
import com.example.TechSpot.modules.orders.dto.OrderResponse;
import com.example.TechSpot.modules.users.entity.User;
import com.example.TechSpot.modules.cart.exception.EmptyCartException;
import com.example.TechSpot.modules.cart.service.command.CommandCartService;
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
	private final UserRepositoryProvider userRepositoryProvider;
	private final OrderMapper orderMapper;
	private final OrderCreationService orderCreationService;
	private final CartOrderProvider cartOrderProvider;

	@Transactional
	public OrderResponse checkout(UUID userId) {
		log.info("Начало оформления заказа для пользователя: {}", userId);

		User user = userRepositoryProvider.findById(userId);
		Cart cart = cartOrderProvider.ensureUserHasCart(user);

		if (cart == null || cart.getCartItems().isEmpty()) {
			log.warn("Попытка оформления заказа с пустой корзиной для пользователя: {}", userId);
			throw new EmptyCartException();
		}

		log.info("Корзина пользователя {} содержит {} товаров", userId, cart.getCartItems().size());
		orderCreationService.updateProductQuantities(cart);

		Order order = orderCreationService.createOrderFromCart(user, cart);
		Order savedOrder = orderRepository.save(order);
		cartOrderProvider.clearCart(userId);

		log.info("Заказ успешно оформлен. Номер заказа: {}, ID: {}", savedOrder.getOrderNumber(), savedOrder.getId());
		return orderMapper.toOrderResponse(savedOrder);
	}



}