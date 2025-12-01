package com.example.TechSpot.modules.products.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Запрос на создание нового товара")
public record ProductCreateRequest(
		@Schema(
				description = "Название товара",
				example = "iPhone 15 Pro",
				minLength = 2,
				maxLength = 50
		)
		@NotBlank(message = "Имя продукта обязательно для заполнения")
		@Size(min = 2, max = 50, message = "Название должно быть от 2 до 50 символов")
		String productName,

		@Schema(
				description = "Цена товара",
				example = "999.99",
				minimum = "0.01"
		)
		@NotNull(message = "Цена обязательна для заполнения")
		@DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
		@Digits(integer = 10, fraction = 2, message = "Цена должна иметь максимум 2 знака после запятой")
		BigDecimal price,

		@Schema(
				description = "Количество товара на складе",
				example = "100",
				minimum = "0",
				maximum = "10000"
		)
		@Min(value = 0, message = "Количество не может быть отрицательным")
		@Max(value = 10000, message = "Слишком большое количество")
		int quantity,

		@Schema(
				description = "Описание товара",
				example = "Новый iPhone 15 Pro с революционной камерой и процессором A17 Pro",
				minLength = 10,
				maxLength = 1000
		)
		@NotBlank(message = "Описание обязательно для заполнения")
		@Size(min = 10, max = 1000, message = "Описание должно быть от 10 до 1000 символов")
		String description,

		@Schema(description = "ID категории товара", example = "1")
		@NotNull(message = "ID категории обязателен")
		Long categoryId // ← Используем ID существующей категории
) {}