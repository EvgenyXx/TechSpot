package com.example.TechSpot.dto.product;

import com.example.TechSpot.entity.ProductCategory;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductCreateRequest(

		@NotBlank(message = "Имя продукта обязательно для заполнения")
		@Size(min = 2, max = 50, message = "Название должно быть от 2 до 50 символов")
		String productName,

		@NotNull(message = "Цена обязательна для заполнения")
		@DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
		@Digits(integer = 10, fraction = 2, message = "Цена должна иметь максимум 2 знака после запятой")
		BigDecimal price,

		@Min(value = 0, message = "Количество не может быть отрицательным")
		@Max(value = 10000, message = "Слишком большое количество")
		int quantity,

		@NotBlank(message = "Описание обязательно для заполнения")
		@Size(min = 10, max = 1000, message = "Описание должно быть от 10 до 1000 символов")
		String description,

		@NotNull(message = "Категория обязательна для выбора")
		ProductCategory category

//		@NotNull(message = "Id обязателен")
//		UUID userId

) {}