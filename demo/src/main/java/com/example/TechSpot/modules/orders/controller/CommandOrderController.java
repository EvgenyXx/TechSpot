package com.example.TechSpot.modules.orders.controller;

import com.example.TechSpot.common.constants.ApiPaths;

import static com.example.TechSpot.common.constants.SecurityRoles.IS_USER;

import com.example.TechSpot.core.security.CustomUserDetail;
import com.example.TechSpot.modules.orders.service.command.CommandOrderService;
import com.example.TechSpot.modules.orders.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.ORDER_BASE)
@RequiredArgsConstructor
@Tag(name = "Orders", description = "API для управления заказами")
@Log4j2
public class CommandOrderController {

	private final CommandOrderService commandOrderService;

	@Operation(
			summary = "Оформить заказ",
			description = """
					Создает новый заказ на основе товаров в корзине пользователя.
					После успешного оформления корзина очищается.
					
					**Бизнес-логика:**
					- Проверяет наличие товаров на складе
					- Резервирует товары
					- Создает заказ со статусом "Обрабатывается"
					- Очищает корзину пользователя
					"""
	)
	@ApiResponse(responseCode = "201", description = "Заказ успешно создан")
	@ApiResponse(responseCode = "400", description = "Корзина пуста или невалидные данные")
	@ApiResponse(responseCode = "404", description = "Пользователь не найден")
	@ApiResponse(responseCode = "409", description = "Недостаточно товара на складе")
	@PostMapping(ApiPaths.CHECKOUT)
	@PreAuthorize(IS_USER)
	public ResponseEntity<OrderResponse> checkout(@AuthenticationPrincipal CustomUserDetail user) {
		OrderResponse orderResponse = commandOrderService.checkout(user.id());
		return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
	}



}