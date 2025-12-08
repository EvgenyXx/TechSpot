package com.example.techspot.modules.cart.application.command;

import com.example.techspot.modules.cart.domain.entity.Cart;
import com.example.techspot.modules.cart.infrastructure.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartPricingService {

	private final CartCalculator calculator;
	private final CartRepository cartRepository;

	public void recalculate(Cart cart) {
		calculator.recalculateCartTotal(cart);
		cartRepository.save(cart);
	}
}
