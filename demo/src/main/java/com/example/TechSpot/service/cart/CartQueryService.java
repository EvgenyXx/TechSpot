package com.example.TechSpot.service.cart;


import com.example.TechSpot.dto.cart.response.CartResponse;
import com.example.TechSpot.entity.Cart;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.mapping.CartMapper;
import com.example.TechSpot.service.user.query.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class CartQueryService {

	private final UserFinder userFinder;
	private final CartInitializationService cartInitializationService;
	private final CartMapper cartMapper;


	@Transactional(readOnly = true)
	public CartResponse getCart(UUID userId) {
		log.info("Запрос на получение корзины пользователя: {}", userId);
		User user = userFinder.findById(userId);
		Cart cart = cartInitializationService.ensureUserHasCart(user);
		log.info("Корзина пользователя {} загружена, количество позиций: {}", userId, cart.getCartItems().size());
		return cartMapper.toCart(cart);
	}
}
