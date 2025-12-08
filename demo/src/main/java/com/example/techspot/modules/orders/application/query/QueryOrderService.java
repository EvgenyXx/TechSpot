package com.example.techspot.modules.orders.application.query;


import com.example.techspot.modules.orders.application.dto.OrderResponse;
import com.example.techspot.modules.orders.domain.entity.Order;
import com.example.techspot.modules.orders.application.exception.OrderNotFoundException;
import com.example.techspot.modules.orders.infrastructure.repository.OrderRepository;
import com.example.techspot.modules.orders.application.factory.OrderResponseFactory;
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
	private final OrderResponseFactory orderResponseFactory;


	@Transactional(readOnly = true)
	public List<OrderResponse> getOrderHistory(UUID userId) {
		log.info("Начался поиск истории заказов для пользователя {}", userId);

		List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);

		log.info("Найдено {} заказов для пользователя {}", orders.size(), userId);
		return orders.stream()
				.map(orderResponseFactory::build)
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
		return orderResponseFactory.build(order);
	}
}
