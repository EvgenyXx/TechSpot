package com.example.techspot.modules.reviews.api;

import com.example.techspot.common.constants.ApiPaths;
import com.example.techspot.core.security.CustomUserDetail;
import com.example.techspot.modules.reviews.application.command.ReviewCommandService;
import com.example.techspot.modules.reviews.application.dto.ReviewCreateRequest;
import com.example.techspot.modules.reviews.application.dto.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.REVIEWS_BASE)
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "API для управления отзывами о товарах")
public class CommandReviewController {

	private final ReviewCommandService reviewCommandService;

	@Operation(
			summary = "Добавить отзыв к товару",
			description = """
            Создает новый отзыв для указанного товара.
            Пользователь может оставить только один отзыв на товар.
            
            **Требования:**
            - Пользователь должен быть аутентифицирован
            - Товар должен существовать
            - Пользователь не должен был уже оставлять отзыв на этот товар
            
            **Ограничения:**
            - Оценка: от 1 до 5 звезд
            - Комментарий: от 10 до 1000 символов
            """
	)
	@ApiResponse(responseCode = "200", description = "Отзыв успешно добавлен")
	@ApiResponse(responseCode = "400", description = "Невалидные данные отзыва")
	@ApiResponse(responseCode = "401", description = "Требуется аутентификация")
	@ApiResponse(responseCode = "404", description = "Товар не найден")
	@ApiResponse(responseCode = "409", description = "Отзыв уже существует для этого товара")
	@PostMapping(ApiPaths.REVIEW_PRODUCT + ApiPaths.PRODUCT_ID)
	public ResponseEntity<ReviewResponse> addReview(
			@AuthenticationPrincipal CustomUserDetail customUserDetail,
			@PathVariable Long productId,
			@RequestBody @Valid ReviewCreateRequest reviewCreateRequest
	) {
		log.info("HTTP POST {}{} - Добавление отзыва к товару ID: {}",
				ApiPaths.REVIEWS_BASE, ApiPaths.REVIEW_PRODUCT, productId);

		ReviewResponse response = reviewCommandService.addReview(productId, reviewCreateRequest, customUserDetail.id());

		log.info("HTTP 200 отзыв успешно добавлен ID: {}", response.id());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}




	@Operation(
			summary = "Удалить отзыв",
			description = "Удаляет отзыв. Пользователь может удалить только свои отзывы"
	)
	@ApiResponse(responseCode = "204", description = "Отзыв успешно удален")
	@ApiResponse(responseCode = "403", description = "Нет прав для удаления этого отзыва")
	@ApiResponse(responseCode = "404", description = "Отзыв не найден")
	@DeleteMapping(ApiPaths.REVIEW_ID)
	public ResponseEntity<Void> deleteReview(
			@PathVariable Long reviewId,
			@AuthenticationPrincipal CustomUserDetail customUserDetail) {
		log.info("HTTP DELETE {}{} - Удаление отзыва ID: {}",
				ApiPaths.REVIEWS_BASE, ApiPaths.REVIEW_ID, reviewId);

		reviewCommandService.deleteReview(reviewId, customUserDetail.id());

		log.info("HTTP 204 отзыв удален ID: {}", reviewId);
		return ResponseEntity.noContent().build();
	}



}