package com.example.TechSpot.service.order;


import com.example.TechSpot.dto.order.OrderResponse;
import com.example.TechSpot.entity.*;
import com.example.TechSpot.exception.OrderNotFoundException;
import com.example.TechSpot.exception.cart.EmptyCartException;
import com.example.TechSpot.exception.product.InsufficientStockException;
import com.example.TechSpot.mapping.OrderMapper;
import com.example.TechSpot.repository.OrderRepository;
import com.example.TechSpot.service.cart.CartService;
import com.example.TechSpot.service.product.ProductCommandService;
import com.example.TechSpot.service.product.ProductFinder;
import com.example.TechSpot.service.user.UserFinder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	private final UserFinder userFinder;
	private final CartService cartService;
	private final OrderMapper orderMapper;
	private final ProductCommandService commandService;



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

		return orderMapper.toOrderResponse(saveOrder);
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


		Order order = Order.builder()
				.orderNumber(generateOrderNumber())
				.user(user)
				.orderStatus(OrderStatus.CREATED)
				.totalPrice(cart.getTotalPrice())
				.orderItems(new HashSet<>())
				.build();


		for (CartItems cartItem : cart.getCartItems()) {
			OrderItems orderItem = OrderItems.builder()
					.order(order)
					.product(cartItem.getProduct())
					.productName(cartItem.getProduct().getProductName())
					.itemPrice(cartItem.getProduct().getPrice())
					.quantity(cartItem.getQuantity())
					.build();

			order.getOrderItems().add(orderItem);
		}

		return order;
	}

	private String generateOrderNumber() {
		String timestamp = Instant.now().toEpochMilli() + ""; // Unix timestamp
		String random = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
		return "ORD-" + timestamp.substring(0, 10) + "-" + random;
	}




	public List<OrderResponse> getOrderHistory(UUID userId) {
		log.info("Начался поиск всех заказов текущего пользователя {}",userId);

		userFinder.findById(userId);
		List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
		log.info("Поиск всех заказов текущего завершился {}",userId);
		return orders.stream()
				.map(orderMapper::toOrderResponse)
				.toList();
	}

	public OrderResponse getOrderById (UUID userId,Long orderId){
		log.info("Начался поиск заказа пользователя {}",userId);
		Order order = orderRepository.findByIdAndUserId(orderId,userId)
				.orElseThrow(OrderNotFoundException::new);
		log.info("Поиск заказа успешно был завершен");
		return orderMapper.toOrderResponse(order);
	}


}
