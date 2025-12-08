package com.example.techspot.modules.discount.application.strategy;

import com.example.techspot.modules.discount.domain.entity.Discount;

import java.math.BigDecimal;

public interface DiscountStrategy {
	boolean supports(Discount discount);
	BigDecimal apply(BigDecimal basePrice, Discount discount, int quantity);
}
