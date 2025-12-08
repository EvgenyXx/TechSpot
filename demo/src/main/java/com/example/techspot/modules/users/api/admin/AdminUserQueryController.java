package com.example.techspot.modules.users.api.admin;

import com.example.techspot.common.constants.ApiPaths;
import com.example.techspot.modules.users.application.dto.response.UserDetailResponse;
import com.example.techspot.modules.users.application.dto.response.UserResponse;
import com.example.techspot.modules.users.application.query.AdminUserQueryService;
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
import static com.example.techspot.common.constants.SecurityRoles.IS_ADMIN;

import java.util.UUID;


@Tag(name = "Admin Users", description = "API для запросов пользователей администратором")
@RestController
@RequestMapping(ApiPaths.ADMIN_BASE + "/users")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize(IS_ADMIN)
public class AdminUserQueryController {

	private final AdminUserQueryService adminUserQueryService;

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
		Page<UserResponse> allUsers = adminUserQueryService.getAllUsers(pageable);

		log.info("Успешно возвращено {} пользователей на странице {}",
				allUsers.getNumberOfElements(), page);

		return ResponseEntity.ok(allUsers);
	}

	@Operation(
			summary = "Получить детальную информацию о пользователе",
			description = "Возвращает детальную информацию о пользователе включая заказы, отзывы и статистику"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Детальная информация о пользователе получена"),
			@ApiResponse(responseCode = "404", description = "Пользователь не найден"),
			@ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
			@ApiResponse(responseCode = "403", description = "Недостаточно прав")
	})
	@GetMapping(ApiPaths.USER_ID)
	public ResponseEntity<UserDetailResponse> getUserDetail(
			@Parameter(description = "UUID пользователя", example = "123e4567-e89b-12d3-a456-426614174000")
			@PathVariable UUID userId) {

		log.info("Запрос детальной информации пользователя: {}", userId);

		UserDetailResponse userDetailResponse = adminUserQueryService.getUserDetail(userId);

		log.info("Детальная информация пользователя {} успешно получена: orders={}, reviews={}",
				userId, userDetailResponse.ordersCount(), userDetailResponse.reviewsCount());

		return ResponseEntity.ok(userDetailResponse);
	}
}