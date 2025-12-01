package com.example.TechSpot.modules.orders.service.query;


import com.example.TechSpot.modules.api.user.UserRepositoryProvider;
import com.example.TechSpot.modules.orders.dto.OrderResponse;
import com.example.TechSpot.modules.orders.entity.Order;
import com.example.TechSpot.modules.orders.exception.OrderNotFoundException;
import com.example.TechSpot.modules.orders.mapper.OrderMapper;
import com.example.TechSpot.modules.orders.repository.OrderRepository;
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
	private final UserRepositoryProvider userRepositoryProvider;
	private final OrderMapper orderMapper;


	@Transactional(readOnly = true)
	public List<OrderResponse> getOrderHistory(UUID userId) {
		log.info("Начался поиск истории заказов для пользователя {}", userId);

		userRepositoryProvider.findById(userId);
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
