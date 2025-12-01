package com.example.TechSpot.modules.auth.controller;

import com.example.TechSpot.common.constants.ApiPaths;
import com.example.TechSpot.modules.auth.dto.ResetPasswordRequest;
import com.example.TechSpot.modules.auth.service.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping(ApiPaths.PASSWORD_BASE)
@Tag(name = "Authentication", description = "API для восстановления пароля - отправка кода и сброс пароля")
public class PasswordResetController {

	private final PasswordResetService passwordResetService;

	@Operation(
			summary = "Запрос на сброс пароля",
			description = "Отправляет 6-значный код на email для сброса пароля. Код действителен 15 минут."
	)
	@ApiResponse(responseCode = "200", description = "Код сброса пароля успешно отправлен на email")
	@ApiResponse(responseCode = "404", description = "Пользователь с указанным email не найден")
	@ApiResponse(responseCode = "500", description = "Ошибка при отправке email")
	@PostMapping(ApiPaths.PASSWORD_FORGOT)
	public ResponseEntity<Void> forgotPassword(@RequestParam String email) {
		log.info("POST {}{} - запрос сброса пароля для email: {}",
				ApiPaths.PASSWORD_BASE, ApiPaths.PASSWORD_FORGOT, email);
		passwordResetService.forgotPassword(email);
		log.info("Код сброса пароля успешно отправлен для email: {}", email);
		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "Сброс пароля",
			description = "Подтверждает сброс пароля с использованием кода из email. Устанавливает новый пароль для аккаунта."
	)
	@ApiResponse(responseCode = "200", description = "Пароль успешно изменен")
	@ApiResponse(responseCode = "400", description = "Неверный или просроченный код сброса")
	@ApiResponse(responseCode = "404", description = "Пользователь не найден")
	@ApiResponse(responseCode = "422", description = "Новый пароль не соответствует требованиям безопасности")
	@PostMapping(ApiPaths.PASSWORD_RESET)
	public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
		log.info("POST {}{} - сброс пароля для email: {}",
				ApiPaths.PASSWORD_BASE, ApiPaths.PASSWORD_RESET, request.email());
		passwordResetService.resetPassword(request);
		log.info("Пароль успешно сброшен для email: {}", request.email());
		return ResponseEntity.ok().build();
	}
}