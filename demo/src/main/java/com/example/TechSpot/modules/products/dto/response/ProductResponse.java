package com.example.TechSpot.modules.products.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Ответ с информацией о товаре")
public record ProductResponse(
		@Schema(description = "ID товара", example = "123")
		Long id,

		@Schema(description = "Название товара", example = "iPhone 15 Pro")
		String productName,

		@Schema(description = "Цена товара без скидки", example = "999.99")
		BigDecimal price,

		@Schema(description = "Цена товара со скидкой (если скидки нет — null)", example = "899.99")
		BigDecimal discountedPrice,

		@Schema(description = "Количество товара на складе", example = "50")
		Integer quantity,

		@Schema(description = "Описание товара")
		String description,

		@Schema(description = "Категория товара")
		String productCategory,

		@Schema(description = "Имя продавца")
		String customerName,

		@Schema(description = "Email продавца")
		String sellerEmail,

		@Schema(description = "Дата создания")
		LocalDateTime createdAt,

		@Schema(description = "Дата обновления")
		LocalDateTime updatedAt,

		@Schema(description = "Доступен ли товар")
		Boolean isAvailable,

		@Schema(description = "Отображаемое название категории")
		String categoryDisplayName
) implements Serializable {}
