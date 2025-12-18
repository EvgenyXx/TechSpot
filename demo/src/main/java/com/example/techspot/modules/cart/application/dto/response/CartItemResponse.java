package com.example.techspot.modules.cart.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Элемент корзины с подробной информацией о товаре")
public record CartItemResponse(

		@Schema(description = "ID товара", example = "15")
		Long productId,

		@Schema(description = "Название товара", example = "iPhone 15 Pro")
		String productName,

		@Schema(description = "URL основного изображения товара",
				example = "https://cdn.techspot.com/products/iphone15pro.jpg")
		String imageUrl,

		@Schema(description = "Обычная цена товара", example = "99990.00")
		BigDecimal price,

		@Schema(description = "Цена товара со скидкой", example = "74990.00")
		BigDecimal discountedPrice,

		@Schema(description = "Количество единиц товара в корзине", example = "2")
		Integer quantity,

		@Schema(description = "Общая цена = discountedPrice * quantity", example = "149980.00")
		BigDecimal totalPrice


) {}
