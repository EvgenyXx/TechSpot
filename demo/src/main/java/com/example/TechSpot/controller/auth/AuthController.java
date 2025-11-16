package com.example.TechSpot.controller.auth;

import com.example.TechSpot.constants.ApiPaths;
import com.example.TechSpot.dto.user.LoginRequest;
import com.example.TechSpot.dto.user.UpdateProfileRequest;
import com.example.TechSpot.dto.user.UserRequest;
import com.example.TechSpot.dto.user.UserResponse;
import com.example.TechSpot.security.CustomUserDetail;
import com.example.TechSpot.service.user.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
	public ResponseEntity<UserResponse> login(@RequestBody @Valid LoginRequest request) {
		log.info("HTTP POST api/auth/login {}", request.email());
		UserResponse response = authService.login(request);
		log.info("HTTP 200 успешная авторизация {}", request.email());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Operation(
			summary = "Получить текущего пользователя",
			description = "Возвращает данные аутентифицированного пользователя. Требует активной сессии."
	)
	@ApiResponse(responseCode = "200", description = "Данные пользователя успешно получены")
	@ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
	@GetMapping(ApiPaths.CURRENT_USER)
	@PreAuthorize(IS_USER)
	public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal CustomUserDetail userDetail) {
		log.info("HTTP GET api/auth/me {}", userDetail.email());
		UserResponse response = authService.getCurrentUser(userDetail.id());
		log.info("HTTP 200 успешно получили текущего пользователя {}", response.email());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Operation(
			summary = "Обновление профиля пользователя",
			description = "Обновляет данные профиля текущего пользователя. Обновляются только переданные поля."
	)
	@ApiResponse(responseCode = "200", description = "Профиль успешно обновлен")
	@ApiResponse(responseCode = "400", description = "Невалидные данные")
	@ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
	@PutMapping(ApiPaths.UPDATE_PROFILE)
	@PreAuthorize(IS_USER)
	public ResponseEntity<UserResponse> updateProfile(
			@AuthenticationPrincipal CustomUserDetail customUserDetail,
			@RequestBody @Valid UpdateProfileRequest request) {

		log.info("HTTP PUT api/auth/profile {}", customUserDetail.email());
		UserResponse response = authService.updateProfile(customUserDetail.id(), request);
		log.info("HTTP 200 успешно обновили профиль текущего пользователя {}", customUserDetail.email());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}