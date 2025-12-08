package com.example.techspot.modules.api.discount;

import com.example.techspot.modules.products.domain.entity.Product;

import java.math.BigDecimal;

public interface DiscountProvider {

	BigDecimal applyDiscount(Product product, int quantity, BigDecimal basePrice);
}