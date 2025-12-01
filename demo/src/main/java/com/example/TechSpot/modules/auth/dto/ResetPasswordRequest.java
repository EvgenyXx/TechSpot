package com.example.TechSpot.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на сброс пароля")
public record ResetPasswordRequest(

		@Schema(description = "Email пользователя", example = "user@example.com")
		@NotBlank(message = "Email не может быть пустым")
		@Email(message = "Некорректный формат email")
		String email,

		@Schema(description = "Код сброса пароля (6 цифр)", example = "123456")
		@NotBlank(message = "Код сброса не может быть пустым")
		@Size(min = 6, max = 6, message = "Код сброса должен содержать 6 цифр")
		String resetCode,

		@Schema(description = "Новый пароль", example = "newSecurePassword123")
		@NotBlank(message = "Новый пароль не может быть пустым")
		@Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
		String newPassword
) {}