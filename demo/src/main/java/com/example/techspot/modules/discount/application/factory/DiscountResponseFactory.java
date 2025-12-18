package com.example.techspot.modules.discount.application.factory;


import com.example.techspot.modules.discount.application.dto.DiscountResponse;
import com.example.techspot.modules.discount.domain.entity.Discount;

import org.springframework.stereotype.Service;

@Service
public class DiscountResponseFactory {

	public DiscountResponse from(Discount discount) {
		return new DiscountResponse(
				discount.getId(),
				discount.getType(),
				discount.getPercentage(),
				discount.getFixedAmount(),
				discount.getStartsAt(),
				discount.getEndsAt(),
				discount.getProduct().getId(),
				null,
				discount.isActive(),
				discount.getMinQuantity()
		);
	}
}
