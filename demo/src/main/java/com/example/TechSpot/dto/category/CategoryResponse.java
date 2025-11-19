package com.example.TechSpot.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными категории")
public record CategoryResponse(
		@Schema(description = "Уникальный идентификатор категории", example = "1")
		Long id,

		@Schema(description = "Название категории", example = "Смартфоны")
		String name,

		@Schema(description = "Slug категории для URL", example = "smartphones")
		String slug,

		@Schema(description = "Есть ли у категории подкатегории", example = "true")
		boolean hasChildren
) {}