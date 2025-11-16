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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.ORDER_BASE)
@RequiredArgsConstructor
@Tag(name = "Orders", description = "API для управления заказами")
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
}