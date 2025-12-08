package com.example.techspot.modules.orders.application.command;


import com.example.techspot.modules.api.cart.CartOrderProvider;
import com.example.techspot.modules.api.user.UserRepositoryProvider;
import com.example.techspot.modules.cart.domain.entity.Cart;
import com.example.techspot.modules.cart.application.exception.EmptyCartException;
import com.example.techspot.modules.users.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderCartValidationAction {

	private final CartOrderProvider cartOrderProvider;
	private final UserRepositoryProvider userRepositoryProvider;

	public Cart validateCart(UUID userId) {
		User user = userRepositoryProvider.findById(userId);
		Cart cart = cartOrderProvider.ensureUserHasCart(user);

		if (cart == null || cart.getCartItems().isEmpty()) {
			throw new EmptyCartException();
		}

		return cart;
	}
}
