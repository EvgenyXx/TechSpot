package com.example.TechSpot.service.cart;


import com.example.TechSpot.entity.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
@Log4j2
public class CartCalculatorService {


	public void recalculateCartTotal(Cart cart) {
		log.debug("Вычисление общей стоимости корзины ID: {}", cart.getId());
		BigDecimal total = cart.getCartItems().stream()
				.map(item -> item.getItemPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		cart.setTotalPrice(total);
		log.debug("Расчет стоимости завершен. Итоговая сумма: {}", total);
	}
}
