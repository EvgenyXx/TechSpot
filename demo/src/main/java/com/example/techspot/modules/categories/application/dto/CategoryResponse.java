package com.example.techspot.modules.categories.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

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
) implements Serializable {}