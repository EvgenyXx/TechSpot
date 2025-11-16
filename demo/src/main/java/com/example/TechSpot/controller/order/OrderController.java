package com.example.TechSpot.controller.order;

import com.example.TechSpot.constants.ApiPaths;

import static com.example.TechSpot.constants.SecurityRoles.IS_USER;

import com.example.TechSpot.dto.order.OrderResponse;
import com.example.TechSpot.security.CustomUserDetail;
import com.example.TechSpot.service.order.OrderService;
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

import java.util.List;

@RestController
@RequestMapping(ApiPaths.ORDER_BASE)
@RequiredArgsConstructor
@Tag(name = "Orders", description = "API для управления заказами")
@Log4j2
public class OrderController {

	private final OrderService orderService;

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
		OrderResponse orderResponse = orderService.checkout(user.id());
		return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
	}

	@Operation(
			summary = "Получить историю заказов",
			description = "Возвращает список всех заказов текущего пользователя"
	)
	@ApiResponse(responseCode = "200", description = "История заказов успешно получена")
	@ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
	@GetMapping
	@PreAuthorize(IS_USER)
	public ResponseEntity<List<OrderResponse>> getUserOrders(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
		log.info("HTTP GET api/order {}", customUserDetail.email());
		List<OrderResponse> response = orderService.getOrderHistory(customUserDetail.id());
		log.info("HTTP 200 успешно получили заказы текущего пользователя {}", customUserDetail.email());
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}



	@Operation(
			summary = "Получить детали заказа",
			description = "Возвращает детальную информацию о конкретном заказе. Проверяет что заказ принадлежит текущему пользователю."
	)
	@ApiResponse(responseCode = "200", description = "Детали заказа успешно получены")
	@ApiResponse(responseCode = "404", description = "Заказ не найден или не принадлежит пользователю")
	@PreAuthorize(IS_USER)
	@GetMapping(ApiPaths.ORDER_ID)
	public ResponseEntity<OrderResponse> getOrderDetails(
			@PathVariable Long orderId,
			@AuthenticationPrincipal CustomUserDetail customUserDetail) {

		log.info("HTTP GET api/order/{} пользователь {}", orderId, customUserDetail.email());
		OrderResponse response = orderService.getOrderById(customUserDetail.id(), orderId);
		log.info("HTTP 200 успешно получили детализированный ответ по заказу {}", orderId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}