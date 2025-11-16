package com.example.TechSpot.dto.product;

import com.example.TechSpot.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Ответ с информацией о товаре")
public record ProductResponse(
		@Schema(description = "ID товара", example = "123")
		Long id,

		@Schema(description = "Название товара", example = "iPhone 15 Pro")
		String productName,

		@Schema(description = "Цена товара", example = "999.99")
		BigDecimal price,

		@Schema(description = "Количество товара на складе", example = "50")
		Integer quantity,

		@Schema(
				description = "Описание товара",
				example = "Новый iPhone 15 Pro с революционной камерой и процессором A17 Pro"
		)
		String description,

		@Schema(description = "Категория товара", example = "ELECTRONICS")
		ProductCategory productCategory,

		@Schema(description = "Имя продавца", example = "Иван Петров")
		String customerName,

		@Schema(description = "Email продавца", example = "ivan@example.com")
		String sellerEmail,

		@Schema(description = "Дата создания товара", example = "2024-01-15T10:30:00")
		LocalDateTime createdAt,

		@Schema(description = "Дата последнего обновления", example = "2024-01-16T14:20:00")
		LocalDateTime updatedAt,

		@Schema(description = "Доступен ли товар для покупки", example = "true")
		Boolean isAvailable,

		@Schema(description = "Отображаемое название категории", example = "Электроника")
		String categoryDisplayName
) {
}