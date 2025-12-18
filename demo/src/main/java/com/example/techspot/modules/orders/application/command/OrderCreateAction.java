package com.example.techspot.modules.orders.application.command;

import com.example.techspot.modules.cart.domain.entity.Cart;
import com.example.techspot.modules.cart.domain.entity.CartItems;
import com.example.techspot.modules.orders.domain.entity.Order;
import com.example.techspot.modules.orders.domain.entity.OrderItems;
import com.example.techspot.modules.orders.domain.entity.OrderStatus;
import com.example.techspot.modules.orders.infrastructure.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderCreateAction {

	private final OrderRepository orderRepository;

	public Order create(Cart cart) {

		Order order = new Order();
		order.setOrderNumber(generateNumber());
		order.setUser(cart.getUser());
		order.setOrderStatus(OrderStatus.CREATED);
		order.setTotalPrice(cart.getTotalPrice());

		Set<OrderItems> items = new HashSet<>();
		for (CartItems cartItem : cart.getCartItems()) {
			items.add(
					OrderItems.builder()
							.order(order)
							.product(cartItem.getProduct())
							.productName(cartItem.getProduct().getProductName())
							.itemPrice(cartItem.getProduct().getPrice())
							.quantity(cartItem.getQuantity())
							.build()
			);
		}
		order.setOrderItems(items);
		return orderRepository.save(order);
	}

	private String generateNumber() {
		return "ORD-" + System.currentTimeMillis();
	}
}
