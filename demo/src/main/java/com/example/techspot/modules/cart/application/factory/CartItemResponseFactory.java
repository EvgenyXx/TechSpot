package com.example.techspot.modules.cart.application.factory;

import com.example.techspot.modules.api.discount.DiscountProvider;
import com.example.techspot.modules.cart.application.dto.response.CartItemResponse;
import com.example.techspot.modules.cart.domain.entity.CartItems;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class CartItemResponseFactory {

	private final DiscountProvider discountProvider;


	public CartItemResponse build(CartItems item) {

		var product = item.getProduct();

		BigDecimal basePrice = product.getPrice();
		BigDecimal discountedPrice = discountProvider.applyDiscount(product, item.getQuantity(), basePrice)
				.setScale(2, RoundingMode.HALF_UP);

		BigDecimal total = discountedPrice.multiply(BigDecimal.valueOf(item.getQuantity()))
				.setScale(2,RoundingMode.HALF_UP);

		return new CartItemResponse(
				product.getId(),
				product.getProductName(),
				null,   // если есть
				basePrice,
				discountedPrice,
				item.getQuantity(),
				total
		);
	}
}
