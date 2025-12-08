package com.example.techspot.modules.cart.application.command;

import com.example.techspot.modules.api.product.ProductProvider;
import com.example.techspot.modules.cart.domain.entity.Cart;
import com.example.techspot.modules.cart.domain.entity.CartItems;
import com.example.techspot.modules.cart.application.exception.CartItemsNotFoundException;
import com.example.techspot.modules.cart.domain.service.CartItemManager;
import com.example.techspot.modules.products.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartOperationService {

	private final ProductProvider products;

	private final CartItemManager itemManager;

	public void add(Cart cart, Long productId, int quantity) {
		Product product = products.findById(productId);

		itemManager.createOrUpdateCartItems(cart, product, quantity);
	}

	public void remove(Cart cart, Long cartItemId) {
		boolean removed = cart.getCartItems()
				.removeIf(i -> i.getId().equals(cartItemId));

		if (!removed)
			throw new CartItemsNotFoundException();
	}

	public void updateQuantity(Cart cart, Long itemId, int newQty) {
		CartItems item = cart.getCartItems().stream()
				.filter(i -> i.getId().equals(itemId))
				.findFirst()
				.orElseThrow(CartItemsNotFoundException::new);

		item.setQuantity(newQty);
	}

	public void clear(Cart cart) {
		cart.getCartItems().clear();
		cart.setTotalPrice(BigDecimal.ZERO);
	}
}
