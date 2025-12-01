package com.example.TechSpot.modules.products.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Запрос на обновление товара")
public record ProductUpdateRequest(

		@Schema(
				description = "Название товара",
				example = "iPhone 15 Pro Max",
				minLength = 2,
				maxLength = 50,
				nullable = true
		)
		@Size(min = 2, max = 50, message = "Название должно быть от 2 до 50 символов")
		String productName,

		@Schema(
				description = "Описание товара",
				example = "Обновленная версия с улучшенной камерой",
				minLength = 10,
				maxLength = 1000,
				nullable = true
		)
		@Size(min = 10, max = 1000, message = "Описание должно быть от 10 до 1000 символов")
		String description,

		@Schema(
				description = "Цена товара",
				example = "1199.99",
				minimum = "0.01",
				nullable = true
		)
		@DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
		@Digits(integer = 10, fraction = 2, message = "Цена должна иметь максимум 2 знака после запятой")
		BigDecimal price,

		@Schema(
				description = "Количество товара на складе",
				example = "25",
				minimum = "0",
				maximum = "10000",
				nullable = true
		)
		@Min(value = 0, message = "Количество не может быть отрицательным")
		Integer quantity,

		@Schema(
				description = "ID категории товара",
				example = "7",
				nullable = true
		)
		Long categoryId

) {}