package com.example.TechSpot.modules.discount;

import java.math.BigDecimal;

public interface DiscountStrategy {
	boolean supports(Discount discount);
	BigDecimal apply(BigDecimal basePrice, Discount discount, int quantity);
}
