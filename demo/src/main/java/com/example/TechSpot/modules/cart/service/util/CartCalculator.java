package com.example.TechSpot.modules.cart.service.util;


import com.example.TechSpot.modules.api.discount.DiscountProvider;
import com.example.TechSpot.modules.cart.entity.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
@Log4j2
public class CartCalculator {

	private final DiscountProvider discountProvider;

	public void recalculateCartTotal(Cart cart) {
		log.debug("Вычисление общей стоимости корзины ID: {}", cart.getId());

		BigDecimal total = cart.getCartItems().stream()
				.map(item -> {
					BigDecimal basePrice = item.getItemPrice(); // цена товара без скидки
					int qty = item.getQuantity();

					// применяем скидку через модуль скидок
					BigDecimal finalPrice = discountProvider.applyDiscount(
							item.getProduct(),
							qty,
							basePrice
					);

					// итоговая цена позиции = цена * количество
					return finalPrice.multiply(BigDecimal.valueOf(qty));
				})
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		cart.setTotalPrice(total);

		log.debug("Расчет стоимости завершен. Итоговая сумма: {}", total);
	}
}

