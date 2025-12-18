package com.example.techspot.modules.orders.api;


import com.example.techspot.common.constants.ApiPaths;
import com.example.techspot.core.security.CustomUserDetail;
import com.example.techspot.modules.orders.application.dto.OrderResponse;
import com.example.techspot.modules.orders.application.query.QueryOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.techspot.common.constants.SecurityRoles.IS_USER;

@RequestMapping(ApiPaths.ORDER_BASE)
@RestController
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Orders", description = "API для запросов заказов пользователя")
@PreAuthorize(IS_USER)
public class QueryOrderController {

	private final QueryOrderService queryOrderService;



	@Operation(
			summary = "Получить историю заказов",
			description = "Возвращает список всех заказов текущего пользователя"
	)
	@ApiResponse(responseCode = "200", description = "История заказов успешно получена")
	@ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getUserOrders(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
		log.info("HTTP GET api/order {}", customUserDetail.email());
		List<OrderResponse> response = queryOrderService.getOrderHistory(customUserDetail.id());
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
	@GetMapping(ApiPaths.ORDER_ID)
	public ResponseEntity<OrderResponse> getOrderDetails(
			@PathVariable Long orderId,
			@AuthenticationPrincipal CustomUserDetail customUserDetail) {

		log.info("HTTP GET api/order/{} пользователь {}", orderId, customUserDetail.email());
		OrderResponse response = queryOrderService.getOrderById(customUserDetail.id(), orderId);
		log.info("HTTP 200 успешно получили детализированный ответ по заказу {}", orderId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
