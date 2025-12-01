package com.example.TechSpot.modules.users.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
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
		String phoneNumber,

		@Schema(
				description = "Статус пользователя",
				example = "true или false"
		)
		boolean isActive
) implements Serializable {
}