package com.example.TechSpot.modules.reviews.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Ответ с отзывом")
public record ReviewResponse(
		@Schema(description = "ID отзыва", example = "1")
		Long id,

		@Schema(description = "ID пользователя", example = "123e4567-e89b-12d3-a456-426614174000")
		UUID userId,

		@Schema(description = "Имя пользователя", example = "Иван Иванов")
		String userName,

		@Schema(description = "Оценка от 1 до 5", example = "5", minimum = "1", maximum = "5")
		Integer rating,

		@Schema(description = "Текст отзыва", example = "Отличный товар, всем рекомендую!")
		String comment,

		@Schema(description = "Количество пользователей, отметивших отзыв как полезный", example = "15")
		Integer helpfulCount,

		@Schema(description = "Дата и время создания отзыва", example = "2024-01-15T14:30:00")
		LocalDateTime createdAt
) {}