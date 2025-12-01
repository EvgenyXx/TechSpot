package com.example.TechSpot.modules.users.dto.response;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserStatistics(
		@Schema(description = "Общее количество пользователей", example = "150")
		Long totalUsers,

		@Schema(description = "Новые пользователи за последние 30 дней", example = "25")
		Long newUsersLast30Days,

		@Schema(description = "Количество активных пользователей", example = "140")
		Long activeUsers,

		@Schema(description = "Количество заблокированных пользователей", example = "10")
		Long blockedUsers,

		@Schema(description = "Время расчета статистики", example = "2024-01-15T14:30:00")
		LocalDateTime calculatedAt
) {}