package com.example.TechSpot.dto.product;

import com.example.TechSpot.entity.ProductCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record ProductResponse(

		Long id,                           // ID товара
		String productName,                // Название товара
		BigDecimal price,                  // Цена
		Integer quantity,                  // Количество в наличии
		String description,                // Описание
		ProductCategory productCategory,   // Категория

		// 🎯 ДАННЫЕ ПРОДАВЦА (для отображения на фронте)
		String customerName     ,         // "Иван Петров" (firstname + lastname)
//		String sellerEmail,                // ivan@example.com (опционально)
//
//		// 🎯 СИСТЕМНЫЕ ДАННЫЕ
		LocalDateTime createdAt,           // Когда создан товар
		LocalDateTime updatedAt          // Когда обновлен
//
//		// 🎯 ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ (опционально)
//		Boolean isAvailable,               // Есть ли в наличии (quantity > 0)
//		String categoryDisplayName         // "Смартфоны" вместо SMARTPHONES
) {
}
