package com.example.TechSpot.modules.cart.service.query;


import com.example.TechSpot.modules.api.user.UserRepositoryProvider;
import com.example.TechSpot.modules.cart.dto.response.CartResponse;
import com.example.TechSpot.modules.cart.entity.Cart;
import com.example.TechSpot.modules.cart.service.command.CartInitializationService;
import com.example.TechSpot.modules.users.entity.User;
import com.example.TechSpot.modules.cart.mapper.CartMapper;
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
