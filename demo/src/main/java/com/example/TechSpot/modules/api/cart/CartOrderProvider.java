package com.example.TechSpot.modules.api.cart;

import com.example.TechSpot.modules.cart.entity.Cart;
import com.example.TechSpot.modules.users.entity.User;

import java.util.UUID;

public interface CartOrderProvider {

	Cart ensureUserHasCart(User user);
	void clearCart(UUID userId);
}
