package com.example.techspot.modules.cart.application.mapper;

import com.example.techspot.modules.cart.application.dto.response.CartItemResponse;
import com.example.techspot.modules.cart.domain.entity.CartItems;
import com.example.techspot.modules.products.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

	// =========================
	//   ГЛАВНЫЙ метод — используется MapStruct-ом
	// =========================
	@Mapping(target = "productId",        source = "cartItems.product.id")
	@Mapping(target = "productName",      source = "cartItems.product.productName")
	@Mapping(target = "quantity",         source = "cartItems.quantity")
	@Mapping(target = "imageUrl",         ignore = true)
	@Mapping(target = "price",            source = "basePrice")
	@Mapping(target = "discountedPrice",  source = "discountedPrice")
	@Mapping(target = "totalPrice",       source = "totalPrice")
	CartItemResponse toCartItem(
			CartItems cartItems,
			BigDecimal basePrice,
			BigDecimal discountedPrice,
			BigDecimal totalPrice

	);

	// =========================
	//   ПРОСТОЙ fallback метод
	//   (НУЖЕН ДЛЯ CartMapper)
	// =========================
	default CartItemResponse toCartItem(CartItems item) {
		BigDecimal base = item.getItemPrice();
		int qty = item.getQuantity();

		BigDecimal discounted = base; // если не считаешь скидки тут
		BigDecimal total = base.multiply(BigDecimal.valueOf(qty));

		return toCartItem(item, base, discounted, total);
	}

	// =========================
	//  Доп. мапперы (если вдруг нужны)
	// =========================
	@Named("toProductName")
	default String toProductName(Product product) {
		return product.getProductName();
	}

	@Named("toPrice")
	default BigDecimal toPrice(Product product) {
		return product.getPrice();
	}
}
