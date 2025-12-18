package com.example.techspot.modules.products.domain.service.validation;

import com.example.techspot.modules.api.discount.DiscountProvider;
import com.example.techspot.modules.products.application.dto.response.ProductCacheModel;
import com.example.techspot.modules.products.application.factory.ProductFactory;
import com.example.techspot.modules.products.domain.entity.Product;
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


	public BigDecimal calculateFor(Product product) {
		return discountProvider.applyDiscount(
				product,
				product.getQuantity(),
				product.getPrice()
		).setScale(2, RoundingMode.HALF_UP);
	}


}
