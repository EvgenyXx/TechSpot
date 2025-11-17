package com.example.TechSpot.controller.product;

import com.example.TechSpot.constants.ApiPaths;
import com.example.TechSpot.dto.product.ProductCreateRequest;
import com.example.TechSpot.dto.product.ProductResponse;
import com.example.TechSpot.dto.product.ProductUpdateRequest;
import com.example.TechSpot.security.CustomUserDetail;
import com.example.TechSpot.service.product.ProductCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.example.TechSpot.constants.SecurityRoles.IS_SELLER;
import static com.example.TechSpot.constants.SecurityRoles.IS_SELLER_OR_ADMIN;



@RestController
@RequestMapping(ApiPaths.COMMAND_BASE)
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Product Management", description = "API для управления товарами (создание, удаление)")
public class ProductCommandController {

	private final ProductCommandService productCommandService;

	@Operation(
			summary = "Создать новый товар",
			description = """
            Создает новый товар в каталоге магазина.
            Доступно только для авторизованных пользователей с соответствующими правами.
            
            **Обязательные поля:**
            - Название товара
            - Цена
            - Категория
            - Описание (опционально)
            """
	)
	@ApiResponse(responseCode = "201", description = "Товар успешно создан")
	@ApiResponse(responseCode = "400", description = "Невалидные данные товара")
	@ApiResponse(responseCode = "401", description = "Требуется авторизация")
	@ApiResponse(responseCode = "403", description = "Недостаточно прав для создания товара")
	@PostMapping(ApiPaths.CREATE_PRODUCT)
	@PreAuthorize(IS_SELLER)
	public ResponseEntity<ProductResponse> createProduct(
			@Valid @RequestBody ProductCreateRequest request,
			@AuthenticationPrincipal CustomUserDetail customUserDetail) {

		log.info("HTTP POST {}{} - Создание товара: {}",
				ApiPaths.COMMAND_BASE, ApiPaths.CREATE_PRODUCT, request.productName());
		log.info("user {}",customUserDetail.email());
		ProductResponse productResponse = productCommandService.createProduct(request, customUserDetail.id());
		log.info("HTTP 201 товар успешно создан: {}", request.productName());

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(productResponse);
	}

	@Operation(
			summary = "Удалить товар",
			description = """
            Удаляет товар из каталога по идентификатору.
            Внимание: операция необратима.
            
            **Требования:**
            - Товар должен существовать
            - Пользователь должен иметь права на удаление
            - Товар не должен быть в активных заказах
            """
	)
	@ApiResponse(responseCode = "204", description = "Товар успешно удален")
	@ApiResponse(responseCode = "401", description = "Требуется авторизация")
	@ApiResponse(responseCode = "403", description = "Недостаточно прав для удаления товара")
	@ApiResponse(responseCode = "404", description = "Товар не найден")
	@ApiResponse(responseCode = "409", description = "Невозможно удалить товар (используется в заказах)")
	@DeleteMapping(ApiPaths.PRODUCT_ID)
	@PreAuthorize(IS_SELLER_OR_ADMIN)
	public ResponseEntity<Void> deleteProduct(
			@PathVariable Long productId,
			@AuthenticationPrincipal CustomUserDetail customUserDetail) {
		log.info("DELETE {}{} - Удаление товара ID: {}",
				ApiPaths.COMMAND_BASE, ApiPaths.PRODUCT_ID, productId);

		productCommandService.deleteProduct(productId, customUserDetail.id());

		return ResponseEntity.noContent().build(); // 204 вместо 501
	}

	@Operation(
			summary = "Обновить товар",
			description = """
            Обновляет данные существующего товара в каталоге.
            Доступно только для продавцов, которые являются владельцами товара.
            
            **Особенности:**
            - Обновляются только переданные поля (частичное обновление)
            - Проверка прав владения товаром
            - Валидация обновляемых данных
            """
	)
	@ApiResponse(responseCode = "200", description = "Товар успешно обновлен")
	@ApiResponse(responseCode = "400", description = "Невалидные данные товара")
	@ApiResponse(responseCode = "401", description = "Требуется авторизация")
	@ApiResponse(responseCode = "403", description = "Недостаточно прав для обновления товара")
	@ApiResponse(responseCode = "404", description = "Товар не найден")
	@PreAuthorize(IS_SELLER)
	@PutMapping(ApiPaths.PRODUCT_ID)
	public ResponseEntity<ProductResponse> updateProduct(
			@AuthenticationPrincipal CustomUserDetail userDetail,
			@PathVariable Long productId,
			@RequestBody @Valid ProductUpdateRequest request){
		log.info("HTTP PUT {}{} - Обновление товара ID: {}",
				ApiPaths.COMMAND_BASE, ApiPaths.PRODUCT_ID, productId);
		ProductResponse response = productCommandService.updateProduct(productId, request, userDetail.id());
		log.info("HTTP 200 товар успешно обновлен: {}", request.productName());
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}
}