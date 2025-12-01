package com.example.TechSpot.modules.cart.service.util;


import com.example.TechSpot.modules.cart.entity.Cart;
import com.example.TechSpot.modules.cart.entity.CartItems;
import com.example.TechSpot.modules.products.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class CartItemManager {


	public void createOrUpdateCartItems(Cart cart, Product product, int quantity) {
		log.debug("Обработка позиции в корзине: productId={}, quantity={}",
				product.getId(), quantity);

		Optional<CartItems> cartItems = findExistingItem(cart, product.getId());

		if (cartItems.isPresent()) {
			CartItems items = cartItems.get();
			int oldQuantity = items.getQuantity();
			items.setQuantity(oldQuantity + quantity);
			log.debug("Количество существующей позиции обновлено: было={}, стало={}",
					oldQuantity, items.getQuantity());
		} else {
			CartItems newCartItems = CartItems.builder()
					.cart(cart)
					.product(product)
					.quantity(quantity)
					.itemPrice(product.getPrice())
					.build();
			cart.getCartItems().add(newCartItems);
			log.debug("Создана новая позиция в корзине для товара: {}", product.getId());
		}
	}

	public Optional<CartItems> findExistingItem(Cart cart, Long productId) {
		log.debug("Поиск товара в корзине: productId={}", productId);
		return cart.getCartItems().stream()
				.filter(cartItems -> cartItems.getProduct().getId().equals(productId))
				.findFirst();
	}


}
