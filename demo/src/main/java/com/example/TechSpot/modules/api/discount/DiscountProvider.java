package com.example.TechSpot.modules.api.discount;

import com.example.TechSpot.modules.products.entity.Product;

import java.math.BigDecimal;

public interface DiscountProvider {

	BigDecimal applyDiscount(Product product, int quantity, BigDecimal basePrice);
}