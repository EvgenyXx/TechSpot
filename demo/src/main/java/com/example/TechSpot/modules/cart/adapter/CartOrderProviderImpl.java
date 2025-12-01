package com.example.TechSpot.modules.cart.adapter;

import com.example.TechSpot.modules.api.cart.CartOrderProvider;
import com.example.TechSpot.modules.cart.entity.Cart;
import com.example.TechSpot.modules.cart.service.command.CartInitializationService;
import com.example.TechSpot.modules.cart.service.command.CommandCartService;
import com.example.TechSpot.modules.users.entity.User;
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
