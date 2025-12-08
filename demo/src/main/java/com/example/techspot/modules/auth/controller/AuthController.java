package com.example.techspot.modules.auth.controller;

import com.example.techspot.common.constants.ApiPaths;
import com.example.techspot.modules.users.application.dto.request.UserRequest;
import com.example.techspot.modules.users.application.dto.response.UserResponse;
import com.example.techspot.modules.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPaths.AUTH_BASE)
@Log4j2
@Tag(name = "Authentication", description = "API для регистрации пользователей")
public class AuthController {

	private final AuthService authService;

	@Operation(summary = "Регистрация пользователя", description = "Создает нового пользователя в системе")
	@ApiResponse(responseCode = "201", description = "Пользователь успешно создан")
	@ApiResponse(responseCode = "400", description = "Невалидные данные")
	@PostMapping(ApiPaths.REGISTER)
	public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest request) {
		log.info(" HTTP POST api/auth/register {}", request.email());
		UserResponse userResponseRegister = authService.register(request);
		log.info("HTTP 201 успешная регистрация {}", request.email());
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponseRegister);
	}







}