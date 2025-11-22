package com.example.TechSpot.controller.admin;

import com.example.TechSpot.constants.ApiPaths;
import com.example.TechSpot.dto.user.UserDetailedStatistics;
import com.example.TechSpot.dto.user.UserStatistics;
import com.example.TechSpot.dto.user.UserResponse;
import com.example.TechSpot.entity.StatisticsPeriod;
import com.example.TechSpot.service.admin.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static com.example.TechSpot.constants.SecurityRoles.IS_ADMIN;

import java.util.UUID;

@Tag(name = "Admin User Management", description = "API для управления пользователями администратором")
@RestController
@RequestMapping(ApiPaths.ADMIN_BASE)
@Log4j2
@RequiredArgsConstructor
@PreAuthorize(IS_ADMIN)
public class AdminUserController {

	private final AdminUserService adminUserService;

	@Operation(
			summary = "Получить список всех пользователей",
			description = "Возвращает paginated список всех зарегистрированных пользователей"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Успешное получение списка пользователей"),
			@ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
			@ApiResponse(responseCode = "403", description = "Недостаточно прав")
	})
	@GetMapping
	public ResponseEntity<Page<UserResponse>> getAllUsers(
			@Parameter(description = "Номер страницы", example = "0")
			@RequestParam(defaultValue = "0") int page,

			@Parameter(description = "Размер страницы", example = "12")
			@RequestParam(defaultValue = "12") int size) {

		log.info("Запрос на получение списка пользователей: page={}, size={}", page, size);

		Pageable pageable = PageRequest.of(page, size);
		Page<UserResponse> allUsers = adminUserService.getAllUsers(pageable);

		log.info("Успешно возвращено {} пользователей на странице {}",
				allUsers.getNumberOfElements(), page);

		return ResponseEntity.ok(allUsers);
	}

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

		adminUserService.toggleUserStatus(userId, isActive);

		log.info("Статус пользователя {} успешно изменен на {}", userId, isActive);

		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "Получить статистику пользователей",
			description = "Возвращает общую статистику по пользователям системы"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Успешное получение статистики"),
			@ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
			@ApiResponse(responseCode = "403", description = "Недостаточно прав")
	})
	@GetMapping(ApiPaths.USER_STATISTICS)
	public ResponseEntity<UserStatistics> getUserStatistics() {

		log.info("Запрос на получение статистики пользователей");

		UserStatistics userStatistics = adminUserService.getUserStatistics();

		log.info("Статистика пользователей успешно получена: totalUsers={}, newUsersLast30Days={}",
				userStatistics.totalUsers(), userStatistics.newUsersLast30Days());

		return ResponseEntity.ok(userStatistics);
	}



	@Operation(
			summary = "Получить расширенную статистику пользователей",
			description = "Возвращает детальную статистику по пользователям за указанный период"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Статистика успешно получена"),
			@ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
			@ApiResponse(responseCode = "403", description = "Недостаточно прав")
	})
	@GetMapping("/statistics/detailed")
	public ResponseEntity<UserDetailedStatistics> getDetailedStatistics(
			@Parameter(description = "Период статистики", example = "LAST_30_DAYS")
			@RequestParam(defaultValue = "LAST_30_DAYS") StatisticsPeriod period) {

		log.info("Запрос на получение расширенной статистики за период: {}", period);

		UserDetailedStatistics statistics = adminUserService.getDetailedStatistics(period);

		log.info("Расширенная статистика успешно получена: totalUsers={}, newUsers={}",
				statistics.totalUsers(), statistics.newUsersInPeriod());

		return ResponseEntity.ok(statistics);
	}
}