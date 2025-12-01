package com.example.TechSpot.modules.cart.controller;

import com.example.TechSpot.common.constants.ApiPaths;
import com.example.TechSpot.modules.cart.dto.response.CartResponse;
import com.example.TechSpot.core.security.CustomUserDetail;
import com.example.TechSpot.modules.cart.service.query.CartQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.TechSpot.common.constants.SecurityRoles.IS_USER;

@RestController
@RequestMapping(ApiPaths.CART_BASE)
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Cart", description = "API для запросов корзины покупок - получение информации о товарах в корзине")
@Schema(description = "Контроллер для операций чтения корзины покупок")
public class QueryCartController {

	private final CartQueryService cartQueryService;

	@Operation(
			summary = "Получить корзину пользователя",
			description = "Возвращает текущее состояние корзины с товарами, включая список позиций, количества и общую стоимость"
	)
	@ApiResponse(responseCode = "200", description = "Корзина успешно получена")
	@GetMapping
	@PreAuthorize(IS_USER)
	public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
		return ResponseEntity.ok(cartQueryService.getCart(customUserDetail.id()));
	}
}