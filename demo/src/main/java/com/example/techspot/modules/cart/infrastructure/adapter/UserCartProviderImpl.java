package com.example.techspot.modules.cart.infrastructure.adapter;

import com.example.techspot.modules.api.cart.UserCartProvider;
import com.example.techspot.modules.cart.domain.entity.Cart;
import com.example.techspot.modules.cart.domain.service.CartInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
public class UserCartProviderImpl implements UserCartProvider {
	private final CartInitializer cartInitializer;

	@Override
	public Cart createDefaultCart() {
		return cartInitializer.createDefaultCart();
	}
}
