package com.example.techspot.modules.discount.application.strategy;


import com.example.techspot.modules.discount.domain.entity.Discount;
import com.example.techspot.modules.discount.domain.entity.DiscountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class QuantityProductStrategy implements DiscountStrategy {

	@Override
	public boolean supports(Discount discount) {
		return discount.getType() == DiscountType.QUANTITY;
	}

	@Override
	public BigDecimal apply(BigDecimal basePrice, Discount discount, int quantity) {
		System.out.println("СКИДКА НА КОЛИЧЕСТВО РАБОТАЕТ");
		if (discount == null){
			return basePrice;
		}

		if (quantity < discount.getMinQuantity()){
			return basePrice;
		}



		BigDecimal percentage = discount.getPercentage()
				.divide(BigDecimal.valueOf(100));

		return basePrice.subtract(basePrice.multiply(percentage))
				.max(BigDecimal.ZERO);
	}
}
