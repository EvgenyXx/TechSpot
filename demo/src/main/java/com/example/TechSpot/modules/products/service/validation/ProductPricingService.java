package com.example.TechSpot.modules.products.service.validation;

import com.example.TechSpot.modules.api.discount.DiscountProvider;
import com.example.TechSpot.modules.products.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class ProductPricingService {
	private final DiscountProvider discountProvider;

	public BigDecimal calculate(Product product) {
		BigDecimal discountPrice =
				discountProvider.applyDiscount(product, product.getQuantity(), product.getPrice());
		return discountPrice.setScale(2, RoundingMode.HALF_UP);
	}
}
