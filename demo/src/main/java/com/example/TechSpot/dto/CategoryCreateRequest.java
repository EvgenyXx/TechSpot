package com.example.TechSpot.dto;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequest(

		// ОБЯЗАТЕЛЬНЫЕ поля:
		@NotBlank(message = "Название категории обязательно")
		@Size(min = 2, max = 50, message = "Название должно быть от 2 до 50 символов")
		String name,

		@NotBlank(message = "Slug категории обязателен")
		@Pattern(regexp = "^[a-z0-9-]+$", message = "Slug может содержать только латинские буквы, цифры и дефисы")
		String slug,

		// ОПЦИОНАЛЬНОЕ поле:
		@Nullable
		Long parentId

) {}