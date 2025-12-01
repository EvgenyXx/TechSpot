package com.example.TechSpot.modules.users.controller.admin;

import com.example.TechSpot.common.constants.ApiPaths;
import com.example.TechSpot.modules.users.service.command.AdminUserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static com.example.TechSpot.common.constants.SecurityRoles.IS_ADMIN;

import java.util.UUID;

@Tag(name = "Admin Users", description = "API для управления пользователями администратором")
@RestController
@RequestMapping(ApiPaths.ADMIN_BASE + "/users")
@Log4j2
@RequiredArgsConstructor
@PreAuthorize(IS_ADMIN)
public class AdminUserCommandController {

	private final AdminUserCommandService adminUserCommandService;

	@Operation(
			summary = "Блокировка/разблокировка пользователя",
			description = "Изменяет статус активности пользователя"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Статус пользователя успешно изменен"),
			@ApiResponse(responseCode = "404", description = "Пользователь не найден"),
			@ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
			@ApiResponse(responseCode = "403", description = "Недостаточно прав")
	})
	@PutMapping(ApiPaths.USER_ID + ApiPaths.USER_STATUS)
	public ResponseEntity<Void> toggleUserStatus(
			@Parameter(description = "UUID пользователя", example = "123e4567-e89b-12d3-a456-426614174000")
			@PathVariable UUID userId,

			@Parameter(description = "Новый статус активности", example = "false")
			@RequestParam boolean isActive) {

		log.info("Запрос на изменение статуса пользователя: userId={}, isActive={}", userId, isActive);

		adminUserCommandService.toggleUserStatus(userId, isActive);

		log.info("Статус пользователя {} успешно изменен на {}", userId, isActive);

		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "Изменение роли пользователя",
			description = "Добавляет новую роль пользователю"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Роль пользователя успешно изменена"),
			@ApiResponse(responseCode = "404", description = "Пользователь не найден"),
			@ApiResponse(responseCode = "409", description = "Пользователь уже имеет эту роль"),
			@ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
			@ApiResponse(responseCode = "403", description = "Недостаточно прав")
	})
	@PutMapping(ApiPaths.USER_ID + "/role")
	public ResponseEntity<Void> updateUserRole(
			@Parameter(description = "UUID пользователя", example = "123e4567-e89b-12d3-a456-426614174000")
			@PathVariable UUID userId,

			@Parameter(description = "Новая роль пользователя", example = "ADMIN")
			@RequestParam String newRole) {

		log.info("Запрос на изменение роли пользователя: userId={}, newRole={}", userId, newRole);

		adminUserCommandService.updateUserRole(userId, newRole);

		log.info("Роль пользователя {} успешно изменена на {}", userId, newRole);

		return ResponseEntity.ok().build();
	}
}