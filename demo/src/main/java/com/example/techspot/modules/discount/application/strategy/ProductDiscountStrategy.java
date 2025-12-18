package com.example.techspot.modules.discount.application.strategy;

import com.example.techspot.modules.discount.domain.entity.Discount;
import com.example.techspot.modules.discount.domain.entity.DiscountType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductDiscountStrategy implements DiscountStrategy {

	@Override
	public boolean supports(Discount discount) {
		return discount.getType() == DiscountType.PRODUCT
				&& discount.getProduct() != null;
	}

	@Override
	public BigDecimal apply(BigDecimal basePrice, Discount discount, int quantity) {

		BigDecimal finalPrice = basePrice;

		// процентная скидка
		if (discount.getPercentage() != null) {
			BigDecimal percent = discount.getPercentage()
					.divide(BigDecimal.valueOf(100));
			finalPrice = finalPrice.subtract(basePrice.multiply(percent));
		}

		// фиксированная скидка
		if (discount.getFixedAmount() != null) {
			finalPrice = finalPrice.subtract(discount.getFixedAmount());
		}

		return finalPrice.max(BigDecimal.ZERO);
	}
}
