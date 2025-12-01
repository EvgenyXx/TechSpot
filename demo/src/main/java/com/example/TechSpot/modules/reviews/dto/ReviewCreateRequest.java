
package com.example.TechSpot.modules.reviews.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Запрос на добавление отзыва")
public record ReviewCreateRequest(
		@Schema(description = "Оценка (1-5)", example = "5")
		@NotNull @Min(1) @Max(5)
		Integer rating,

		@Schema(description = "Комментарий", example = "Отличный товар!")
		@NotBlank @Size(min = 10, max = 1000)
		String comment
) {}