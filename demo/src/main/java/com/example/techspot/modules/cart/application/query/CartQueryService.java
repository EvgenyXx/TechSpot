package com.example.techspot.modules.cart.application.query;


import com.example.techspot.modules.api.user.UserRepositoryProvider;
import com.example.techspot.modules.cart.application.dto.response.CartResponse;
import com.example.techspot.modules.cart.domain.entity.Cart;
import com.example.techspot.modules.cart.application.command.CartInitializationService;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.cart.application.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class CartQueryService {

	private final UserRepositoryProvider userRepositoryProvider;
	private final CartInitializationService cartInitializationService;
	private final CartMapper cartMapper;



	@Transactional(readOnly = true)
	public CartResponse getCart(UUID userId) {
		log.info("Запрос на получение корзины пользователя: {}", userId);
		User user = userRepositoryProvider.findById(userId);
		Cart cart = cartInitializationService.ensureUserHasCart(user);
		log.info("Корзина пользователя {} загружена, количество позиций: {}", userId, cart.getCartItems().size());
		return cartMapper.toCart(cart);
	}



}
