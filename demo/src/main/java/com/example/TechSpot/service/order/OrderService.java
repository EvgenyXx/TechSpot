package com.example.TechSpot.service.order;


import com.example.TechSpot.dto.order.OrderResponse;
import com.example.TechSpot.entity.*;
import com.example.TechSpot.exception.cart.EmptyCartException;
import com.example.TechSpot.exception.product.InsufficientStockException;
import com.example.TechSpot.mapping.OrderMapper;
import com.example.TechSpot.repository.OrderRepository;
import com.example.TechSpot.service.cart.CartService;
import com.example.TechSpot.service.product.ProductCommandService;
import com.example.TechSpot.service.product.ProductFinder;
import com.example.TechSpot.service.user.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final UserFinder userFinder;
	private final CartService cartService;
	private final OrderMapper orderMapper;
	private final ProductFinder productFinder;
	private final ProductCommandService commandService;


	/**
	 * ✅ ОФОРМЛЕНИЕ ЗАКАЗА ИЗ КОРЗИНЫ
	 * Пользователь оформляет заказ из своей корзины
	 */
	public OrderResponse checkout(UUID userId) {
		User user = userFinder.findById(userId);
		Cart cart = cartService.findByUserId(userId);

		if (cart == null || cart.getCartItems().isEmpty()){
			throw new EmptyCartException();
		}

		updateProductQuantities(cart);

		Order order = createOrderFromCart(user, cart);
		Order saveOrder = orderRepository.save(order);
		cartService.clearCart(userId);

		return orderMapper.toOrderResponse(saveOrder); // добавь маппер
	}

	private void updateProductQuantities(Cart cart) {
		for (CartItems cartItem : cart.getCartItems()) {
			boolean success = commandService.reduceQuantity(
					cartItem.getProduct().getId(),
					cartItem.getQuantity()
			);

			if (!success) {
				throw new InsufficientStockException();
			}
		}
	}

	private Order createOrderFromCart(User user, Cart cart) {

		// 1. Создаем Order БЕЗ OrderItems сначала
		Order order = Order.builder()
				.orderNumber(generateOrderNumber())
				.user(user)
				.orderStatus(OrderStatus.CREATED)
				.totalPrice(cart.getTotalPrice()) // цена из корзины
				.orderItems(new HashSet<>()) // пустой набор
				.build();

		// 2. Создаем OrderItems и связываем с Order
		for (CartItems cartItem : cart.getCartItems()) {
			OrderItems orderItem = OrderItems.builder()
					.order(order)           // ← связываем с Order!
					.product(cartItem.getProduct())
					.productName(cartItem.getProduct().getProductName())
					.itemPrice(cartItem.getProduct().getPrice())
					.quantity(cartItem.getQuantity())
					.build();

			// 3. Добавляем в Order
			order.getOrderItems().add(orderItem);
		}

		return order;
	}

	// Самый простой и надежный вариант
	private String generateOrderNumber() {
		String timestamp = Instant.now().toEpochMilli() + ""; // Unix timestamp
		String random = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
		return "ORD-" + timestamp.substring(0, 10) + "-" + random;
	}




	public List<OrderResponse> getOrderHistory(UUID userId) {
		// TODO: 1. Проверить что пользователь существует
		// TODO: 2. Найти все заказы пользователя
		// TODO: 3. Отсортировать по дате создания (новые first)
		// TODO: 4. Преобразовать в OrderResponse
		// TODO: 5. Вернуть список заказов
		userFinder.findById(userId);
		List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);

		return orders.stream()
				.map(orderMapper::toOrderResponse)
				.toList();
	}


}
