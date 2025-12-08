package com.example.techspot.modules.cart.application.command;

import com.example.techspot.modules.api.user.UserRepositoryProvider;
import com.example.techspot.modules.cart.domain.entity.Cart;
import com.example.techspot.modules.users.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartAccessService {

	private final UserRepositoryProvider users;
	private final CartInitializationService initService;

	public Cart getCartForUser(UUID userId) {
		User user = users.findById(userId);
		return initService.ensureUserHasCart(user);
	}
}