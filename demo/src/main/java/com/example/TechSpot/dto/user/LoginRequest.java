package com.example.TechSpot.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для запроса аутентификации пользователя")
public record LoginRequest(

		@Schema(
				description = "Email пользователя",
				example = "user@example.com",
				requiredMode = Schema.RequiredMode.REQUIRED
		)
		@NotBlank(message = "Email не может быть пустым")
		@Email(message = "Некорректный формат email")
		String email,

		@Schema(
				description = "Пароль пользователя",
				example = "mySecurePassword123",
				requiredMode = Schema.RequiredMode.REQUIRED
		)
		@NotBlank(message = "Пароль не может быть пустым")
		@Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
		String password

) {}