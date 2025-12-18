package com.example.techspot.modules.cart.infrastructure.adapter;

import com.example.techspot.modules.api.cart.CartOrderProvider;
import com.example.techspot.modules.cart.domain.entity.Cart;
import com.example.techspot.modules.cart.application.command.CartInitializationService;
import com.example.techspot.modules.cart.application.command.CommandCartService;
import com.example.techspot.modules.users.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Log4j2
public class CartOrderProviderImpl implements CartOrderProvider {

	private final CartInitializationService cartInitializationService;
	private final CommandCartService commandCartService;


	@Override
	public Cart ensureUserHasCart(User user) {
		return cartInitializationService.ensureUserHasCart(user);
	}

	@Override
	public void clearCart(UUID userId) {
		commandCartService.clearCart(userId);
	}
}
