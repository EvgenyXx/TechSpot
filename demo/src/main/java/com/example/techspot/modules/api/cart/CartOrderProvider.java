package com.example.techspot.modules.api.cart;

import com.example.techspot.modules.cart.domain.entity.Cart;
import com.example.techspot.modules.users.domain.entity.User;

import java.util.UUID;

public interface CartOrderProvider {

	Cart ensureUserHasCart(User user);
	void clearCart(UUID userId);
}
