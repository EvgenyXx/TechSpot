package com.example.TechSpot.controller.user;

import com.example.TechSpot.constants.ApiPaths;
import com.example.TechSpot.dto.user.UpdateProfileRequest;
import com.example.TechSpot.dto.user.UserResponse;
import com.example.TechSpot.security.CustomUserDetail;
import com.example.TechSpot.service.user.profile.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.example.TechSpot.constants.SecurityRoles.IS_USER;

@Log4j2
@RestController
@RequestMapping(ApiPaths.PROFILE_BASE)
@RequiredArgsConstructor
@Tag(
		name = "Profile Controller",
		description = "API для управления профилем пользователя - получение и обновление данных профиля"
)
@Schema(description = "Контроллер для работы с профилем пользователя")
public class ProfileController {

	private final ProfileService profileService;

	@Operation(
			summary = "Получить текущего пользователя",
			description = "Возвращает данные аутентифицированного пользователя. Требует активной сессии."
	)
	@ApiResponse(responseCode = "200", description = "Данные пользователя успешно получены")
	@ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
	@GetMapping(ApiPaths.CURRENT_USER)
	@PreAuthorize(IS_USER)
	public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal CustomUserDetail userDetail) {
		log.info("HTTP GET {}{}", ApiPaths.PROFILE_BASE, ApiPaths.CURRENT_USER);
		UserResponse response = profileService.getCurrentUser(userDetail.id());
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
	@PutMapping
	@PreAuthorize(IS_USER)
	public ResponseEntity<UserResponse> updateProfile(
			@AuthenticationPrincipal CustomUserDetail customUserDetail,
			@RequestBody @Valid UpdateProfileRequest request) {

		log.info("HTTP PUT {}", ApiPaths.PROFILE_BASE);
		UserResponse response = profileService.updateProfile(customUserDetail.id(), request);
		log.info("HTTP 200 успешно обновили профиль текущего пользователя {}", customUserDetail.email());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}