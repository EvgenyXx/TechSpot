package com.example.TechSpot.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для обновления профиля пользователя")
public record UpdateProfileRequest(

		@Schema(
				description = "Имя пользователя",
				example = "Иван",
				requiredMode = Schema.RequiredMode.REQUIRED
		)
		@NotBlank(message = "Имя не может быть пустым")
		String firstname,

		@Schema(
				description = "Фамилия пользователя",
				example = "Иванов",
				requiredMode = Schema.RequiredMode.REQUIRED
		)
		@NotBlank(message = "Фамилия не может быть пустой")
		String lastname,

		@Schema(
				description = "Номер телефона",
				example = "+79181234567",
				requiredMode = Schema.RequiredMode.REQUIRED
		)
		@NotBlank(message = "Номер телефона не может быть пустым")
		@Size(min = 12, max = 12, message = "Номер телефона должен содержать 12 символов")
		String phoneNumber

) {}