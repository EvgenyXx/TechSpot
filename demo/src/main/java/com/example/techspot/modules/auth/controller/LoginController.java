package com.example.techspot.modules.auth.controller;


import com.example.techspot.common.constants.ApiPaths;
import com.example.techspot.modules.auth.dto.LoginRequest;
import com.example.techspot.modules.auth.service.LoginService;
import com.example.techspot.modules.users.application.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPaths.AUTH_BASE)
@Log4j2
@Tag(name = "Authentication", description = "API для аутентификации пользователей")
public class LoginController {

	private final LoginService loginService;



	@Operation(
			summary = "Аутентификация пользователя",
			description = "Выполняет вход пользователя в систему. Возвращает данные пользователя при успешной аутентификации."
	)
	@ApiResponse(responseCode = "200", description = "Успешная аутентификация")
	@ApiResponse(responseCode = "400", description = "Невалидные данные запроса")
	@ApiResponse(responseCode = "401", description = "Неверный email или пароль")
	@ApiResponse(responseCode = "404", description = "Пользователь не найден")
	@PostMapping(ApiPaths.LOGIN)
	public ResponseEntity<UserResponse> login(@RequestBody @Valid LoginRequest request,
											  HttpServletRequest httpRequest) {
		log.info("HTTP POST api/auth/login {}", request.email());
		UserResponse response = loginService.login(request, httpRequest);
		log.info("HTTP 200 успешная авторизация {}", request.email());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
