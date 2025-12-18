package com.example.techspot.modules.users.application.dto.response;

import com.example.techspot.modules.users.domain.entity.StatisticsPeriod;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

public record UserDetailedStatistics(
		// 📈 ОСНОВНЫЕ МЕТРИКИ
		@Schema(description = "Общее количество пользователей")
		Long totalUsers,

		@Schema(description = "Новых пользователей за период")
		Long newUsersInPeriod,

		@Schema(description = "Активных пользователей за период")
		Long activeUsersInPeriod,

		// 📅 ДЕТАЛИЗАЦИЯ ПО ПЕРИОДАМ
		@Schema(description = "Регистрации по дням/неделям")
		Map<String, Long> registrationsByDate,

		@Schema(description = "Активность по дням/неделям")
		Map<String, Long> activityByDate,

		// 🎯 БИЗНЕС-МЕТРИКИ
		@Schema(description = "Конверсия регистраций в заказы")
		Double registrationToOrderConversion,

		@Schema(description = "Среднее количество заказов на пользователя")
		Double averageOrdersPerUser,

		@Schema(description = "Процент возвращающихся пользователей")
		Double returningUsersPercentage,

		// 👥 ДЕМОГРАФИЯ
		@Schema(description = "Распределение по ролям")
		Map<String, Long> usersByRole,

		@Schema(description = "Топ самых активных пользователей")
		List<ActiveUser> topActiveUsers,

		@Schema(description = "Период статистики")
		StatisticsPeriod period

) {}
