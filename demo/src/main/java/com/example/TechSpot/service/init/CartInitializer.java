package com.example.TechSpot.service.init;

import com.example.TechSpot.entity.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;

@Log4j2
@Service
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