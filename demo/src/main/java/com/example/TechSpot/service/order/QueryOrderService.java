package com.example.TechSpot.service.order;


import com.example.TechSpot.dto.order.OrderResponse;
import com.example.TechSpot.entity.Order;
import com.example.TechSpot.exception.order.OrderNotFoundException;
import com.example.TechSpot.mapping.OrderMapper;
import com.example.TechSpot.repository.OrderRepository;
import com.example.TechSpot.service.user.query.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class QueryOrderService {

	private final OrderRepository orderRepository;
	private final UserFinder userFinder;
	private final OrderMapper orderMapper;


	@Transactional(readOnly = true)
	public List<OrderResponse> getOrderHistory(UUID userId) {
		log.info("Начался поиск истории заказов для пользователя {}", userId);

		userFinder.findById(userId);
		List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);

		log.info("Найдено {} заказов для пользователя {}", orders.size(), userId);
		return orders.stream()
				.map(orderMapper::toOrderResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public OrderResponse getOrderById(UUID userId, Long orderId) {
		log.info("Поиск заказа ID: {} для пользователя: {}", orderId, userId);

		Order order = orderRepository.findByIdAndUserId(orderId, userId)
				.orElseThrow(() -> {
					log.warn("Заказ с ID {} не найден для пользователя {}", orderId, userId);
					return new OrderNotFoundException();
				});

		log.info("Заказ ID: {} успешно найден для пользователя: {}", orderId, userId);
		return orderMapper.toOrderResponse(order);
	}
}
