package com.example.TechSpot.dto.category;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание категории")
public record CategoryCreateRequest(

		@Schema(
				description = "Название категории",
				example = "Смартфоны",
				minLength = 2,
				maxLength = 50
		)
		@NotBlank(message = "Название категории обязательно")
		@Size(min = 2, max = 50, message = "Название должно быть от 2 до 50 символов")
		String name,

		@Schema(
				description = "Уникальный идентификатор категории для URL",
				example = "smartphones",
				pattern = "^[a-z0-9-]+$"
		)
		@NotBlank(message = "Slug категории обязателен")
		@Pattern(regexp = "^[a-z0-9-]+$", message = "Slug может содержать только латинские буквы, цифры и дефисы")
		String slug,

		@Schema(
				description = "ID родительской категории (для создания подкатегории)",
				example = "1",
				nullable = true
		)
		@Nullable
		Long parentId

) {}