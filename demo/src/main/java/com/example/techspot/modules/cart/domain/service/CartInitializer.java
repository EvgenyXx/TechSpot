package com.example.techspot.modules.cart.domain.service;

import com.example.techspot.modules.cart.domain.entity.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;

@Log4j2
@Component
@RequiredArgsConstructor
public class CartInitializer {

	public Cart createDefaultCart() {
		log.info("Создание корзины по умолчанию");

		Cart cart = Cart.builder()
				.cartItems(new HashSet<>())
				.totalPrice(BigDecimal.ZERO)
				.build();

		log.debug("Корзина по умолчанию создана: {} элементов, общая цена: {}",
				cart.getCartItems().size(), cart.getTotalPrice());
		return cart;
	}
}