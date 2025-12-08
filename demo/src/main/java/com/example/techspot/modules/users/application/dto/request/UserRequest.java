package com.example.techspot.modules.users.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Запрос на регистрацию пользователя")
public record UserRequest(
		@Schema(
				description = "Имя пользователя",
				example = "Иван",
				minLength = 2,
				maxLength = 50
		)
		@NotBlank(message = "First name is required")
		@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
		String firstname,

		@Schema(
				description = "Фамилия пользователя",
				example = "Петров",
				minLength = 2,
				maxLength = 50
		)
		@NotBlank(message = "Last name is required")
		@Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
		String lastname,

		@Schema(
				description = "Email пользователя",
				example = "ivan.petrov@example.com"
		)
		@NotBlank(message = "Email is required")
		@Email(message = "Email should be valid")
		String email,

		@Schema(
				description = "Номер телефона в формате +7XXXXXXXXXX",
				example = "+79123456789"
		)
		@NotBlank(message = "Phone number is required")
		@Pattern(regexp = "^\\+7\\d{10}$", message = "Phone must be in format +79123456789")
		String phoneNumber,

		@Schema(
				description = "Пароль пользователя",
				example = "SecurePassword123",
				minLength = 8
		)
		@NotBlank(message = "Password is required")
		@Size(min = 8, message = "Password must be at least 8 characters")
		String password
) {}