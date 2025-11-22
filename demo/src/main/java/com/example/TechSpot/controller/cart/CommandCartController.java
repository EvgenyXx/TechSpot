package com.example.TechSpot.controller.cart;

import com.example.TechSpot.constants.ApiPaths;
import com.example.TechSpot.dto.cart.request.AddToCartRequest;

import com.example.TechSpot.dto.cart.request.UpdateQuantityRequest;
import com.example.TechSpot.dto.cart.response.CartResponse;
import com.example.TechSpot.security.CustomUserDetail;
import com.example.TechSpot.service.cart.CommandCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;
import static com.example.TechSpot.constants.SecurityRoles.IS_USER;



@RestController
@RequestMapping(ApiPaths.CART_BASE)
@RequiredArgsConstructor
@Tag(name = "Cart", description = "API для управления корзиной покупок")
public class CommandCartController {

	private final CommandCartService commandCartService;



	@Operation(
			summary = "Добавить товар в корзину",
			description = "Добавляет указанный товар в корзину пользователя"
	)
	@ApiResponse(responseCode = "200", description = "Товар успешно добавлен в корзину")
	@ApiResponse(responseCode = "404", description = "Товар не найден")
	@ApiResponse(responseCode = "400", description = "Невалидные данные запроса")
	@PostMapping(ApiPaths.ITEMS)
	@PreAuthorize(IS_USER)
	public ResponseEntity<CartResponse> addItem(
			@RequestBody AddToCartRequest request,
			@AuthenticationPrincipal CustomUserDetail userDetail) {
		return ResponseEntity.ok(commandCartService.addToCart(request, userDetail.id()));
	}

	@Operation(
			summary = "Обновить количество товара в корзине",
			description = "Изменяет количество указанного товара в корзине"
	)
	@ApiResponse(responseCode = "200", description = "Количество успешно обновлено")
	@ApiResponse(responseCode = "404", description = "Товар в корзине не найден")
	@ApiResponse(responseCode = "400", description = "Невалидное количество")
	@PutMapping(ApiPaths.ITEMS_QUANTITY)
	@PreAuthorize(IS_USER)
	public ResponseEntity<CartResponse> updateQuantity(@RequestBody UpdateQuantityRequest request,
													   @AuthenticationPrincipal CustomUserDetail customUserDetail) {
		return ResponseEntity.ok(commandCartService.updateQuantity(request,customUserDetail.id()));
	}

	@Operation(
			summary = "Удалить товар из корзины",
			description = "Удаляет указанный товар из корзины пользователя"
	)
	@ApiResponse(responseCode = "200", description = "Товар успешно удален из корзины")
	@ApiResponse(responseCode = "404", description = "Товар в корзине не найден")
	@DeleteMapping(ApiPaths.REMOVE_ITEM)
	@PreAuthorize(IS_USER)
	public ResponseEntity<CartResponse> removeItem(@PathVariable("cartItemId") Long cartItemId,
												   @AuthenticationPrincipal CustomUserDetail customUserDetail) {
		return ResponseEntity.ok(commandCartService.removeFromCart(cartItemId,customUserDetail.id()));
	}

	@Operation(
			summary = "Очистить корзину",
			description = "Полностью очищает корзину пользователя"
	)
	@ApiResponse(responseCode = "204", description = "Корзина успешно очищена")
	@DeleteMapping(ApiPaths.CLEAR_CART)
	@PreAuthorize(IS_USER)
	public ResponseEntity<Void> clearCart(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
		commandCartService.clearCart(customUserDetail.id());
		return ResponseEntity.noContent().build();
	}
}