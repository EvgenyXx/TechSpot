package com.example.techspot.modules.cart.application.factory;

import com.example.techspot.modules.cart.application.dto.response.CartItemResponse;
import com.example.techspot.modules.cart.application.dto.response.CartResponse;
import com.example.techspot.modules.cart.domain.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartResponseBuilder {

	private final CartItemResponseFactory factory;

	public CartResponse build(Cart cart) {

		Set<CartItemResponse> items = cart.getCartItems().stream()
				.map(factory::build)
				.collect(Collectors.toSet());

		return new CartResponse(items, cart.getTotalPrice().setScale(2, RoundingMode.HALF_UP));
	}
}
