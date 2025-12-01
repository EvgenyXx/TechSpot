package com.example.TechSpot.modules.users.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Детальная информация о пользователе для админки")
public record UserDetailResponse(

		@Schema(description = "UUID пользователя", example = "550e8400-e29b-41d4-a716-446655440000")
		UUID id,

		@Schema(description = "Имя пользователя", example = "Иван")
		String firstname,

		@Schema(description = "Фамилия пользователя", example = "Петров")
		String lastname,

		@Schema(description = "Email пользователя", example = "ivan.petrov@example.com")
		String email,

		@Schema(description = "Номер телефона", example = "+79123456789")
		String phoneNumber,

		@Schema(description = "Статус активности", example = "true")
		boolean isActive,

		@Schema(description = "Роль пользователя", example = "USER")
		Set<String> role,

		@Schema(description = "Дата регистрации", example = "2024-01-15T14:30:00")
		LocalDateTime createdAt,

		@Schema(description = "Дата последнего входа", example = "2024-01-20T10:15:00")
		LocalDateTime lastLogin,

		@Schema(description = "Количество заказов", example = "5")
		Long ordersCount,

		@Schema(description = "Количество отзывов", example = "3")
		Long reviewsCount,

		@Schema(description = "Общая сумма заказов", example = "25500.50")
		Double totalOrdersAmount

) {}