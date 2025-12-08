package com.example.techspot.modules.orders.application.command;

import com.example.techspot.modules.api.cart.CartOrderProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderCartCleanupAction {

	private final CartOrderProvider cartOrderProvider;

	public void clear(UUID userId) {
		cartOrderProvider.clearCart(userId);
	}
}
