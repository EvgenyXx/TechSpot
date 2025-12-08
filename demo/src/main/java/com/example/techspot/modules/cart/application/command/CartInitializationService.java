package com.example.techspot.modules.cart.application.command;


import com.example.techspot.modules.cart.domain.entity.Cart;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.cart.infrastructure.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;

@RequiredArgsConstructor
@Log4j2
@Service
public class CartInitializationService {

	private final CartRepository cartRepository;



	public Cart ensureUserHasCart(User user) {
		if (user.getCart() == null) {
			log.debug("Пользователь {} не имеет корзины, создание новой", user.getId());
			Cart newCart = createNewCart(user);
			user.setCart(newCart);
			return newCart;
		}
		log.debug("Возврат существующей корзины пользователя: {}", user.getId());
		return user.getCart();
	}


	private Cart createNewCart(User user) {
		log.debug("Инициализация новой корзины для пользователя: {}", user.getId());
		Cart newCart = Cart.builder()
				.cartItems(new HashSet<>())
				.user(user)
				.totalPrice(BigDecimal.ZERO)
				.build();
		Cart savedCart = cartRepository.save(newCart);
		log.debug("Корзина создана и сохранена с ID: {}", savedCart.getId());
		return savedCart;
	}

}
