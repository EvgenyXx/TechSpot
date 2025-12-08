package com.example.techspot.modules.orders.application.command;

import com.example.techspot.modules.api.product.ProductStockProvider;
import com.example.techspot.modules.cart.domain.entity.Cart;
import com.example.techspot.modules.cart.domain.entity.CartItems;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStockReservationAction {

	private final ProductStockProvider stockProvider;

	public void reserveStock(Cart cart) {
		for (CartItems item : cart.getCartItems()) {

			stockProvider.reduceQuantity(item.getProduct().getId(), item.getQuantity());

		}
	}
}
