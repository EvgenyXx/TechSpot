package com.example.TechSpot.modules.cart.adapter;

import com.example.TechSpot.modules.api.cart.UserCartProvider;
import com.example.TechSpot.modules.cart.entity.Cart;
import com.example.TechSpot.modules.cart.service.util.CartInitializer;
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
