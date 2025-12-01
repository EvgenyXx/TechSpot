package com.example.TechSpot.modules.users.controller.admin;



import com.example.TechSpot.common.constants.ApiPaths;
import com.example.TechSpot.modules.users.dto.response.UserDetailedStatistics;
import com.example.TechSpot.modules.users.dto.response.UserStatistics;
import com.example.TechSpot.modules.users.entity.StatisticsPeriod;
import com.example.TechSpot.modules.users.service.query.AdminUserStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import static com.example.TechSpot.common.constants.SecurityRoles.IS_ADMIN;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.ADMIN_BASE + "/statistics")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize(IS_ADMIN)
@Tag(name = "Admin Users",description = "API для получения статистики пользователей")
public class AdminUserStatisticsController {

	private final AdminUserStatisticsService adminUserStatisticsService;


	@Operation(
			summary = "Получить расширенную статистику пользователей",
			description = "Возвращает детальную статистику по пользователям за указанный период"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Статистика успешно получена"),
			@ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
			@ApiResponse(responseCode = "403", description = "Недостаточно прав")
	})
	@GetMapping( "/detailed")
	public ResponseEntity<UserDetailedStatistics> getDetailedStatistics(
			@Parameter(description = "Период статистики", example = "LAST_30_DAYS")
			@RequestParam(defaultValue = "LAST_30_DAYS") StatisticsPeriod period) {

		log.info("Запрос на получение расширенной статистики за период: {}", period);

		UserDetailedStatistics statistics = adminUserStatisticsService.getDetailedStatistics(period);

		log.info("Расширенная статистика успешно получена: totalUsers={}, newUsers={}",
				statistics.totalUsers(), statistics.newUsersInPeriod());

		return ResponseEntity.ok(statistics);
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
	@GetMapping
	public ResponseEntity<UserStatistics> getUserStatistics() {

		log.info("Запрос на получение статистики пользователей");

		UserStatistics userStatistics = adminUserStatisticsService.getUserStatistics();

		log.info("Статистика пользователей успешно получена: totalUsers={}, newUsersLast30Days={}",
				userStatistics.totalUsers(), userStatistics.newUsersLast30Days());

		return ResponseEntity.ok(userStatistics);
	}

}
