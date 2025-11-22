package com.example.TechSpot.controller.auth;

import com.example.TechSpot.constants.ApiPaths;
import com.example.TechSpot.dto.user.LoginRequest;
import com.example.TechSpot.dto.user.UpdateProfileRequest;
import com.example.TechSpot.dto.user.UserRequest;
import com.example.TechSpot.dto.user.UserResponse;
import com.example.TechSpot.security.CustomUserDetail;
import com.example.TechSpot.service.user.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.example.TechSpot.constants.SecurityRoles.IS_USER;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPaths.AUTH_BASE)
@Log4j2
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
		UserResponse response = authService.login(request, httpRequest);
		log.info("HTTP 200 успешная авторизация {}", request.email());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}





}