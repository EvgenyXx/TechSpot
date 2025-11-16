package com.example.TechSpot.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Ответ с информацией о пользователе")
public record UserResponse(
		@Schema(
				description = "UUID пользователя",
				example = "550e8400-e29b-41d4-a716-446655440000"
		)
		UUID id,

		@Schema(
				description = "Имя пользователя",
				example = "Иван"
		)
		String firstname,

		@Schema(
				description = "Фамилия пользователя",
				example = "Петров"
		)
		String lastname,

		@Schema(
				description = "Email пользователя",
				example = "ivan.petrov@example.com"
		)
		String email,

		@Schema(
				description = "Номер телефона",
				example = "+79123456789"
		)
		String phoneNumber
) {
}