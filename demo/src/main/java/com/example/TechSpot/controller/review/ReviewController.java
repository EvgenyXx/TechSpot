package com.example.TechSpot.controller.review;

import com.example.TechSpot.constants.ApiPaths;
import com.example.TechSpot.dto.review.ReviewCreateRequest;
import com.example.TechSpot.dto.review.ReviewResponse;
import com.example.TechSpot.security.CustomUserDetail;
import com.example.TechSpot.service.review.ReviewService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.REVIEWS_BASE)
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Review Management", description = "API для управления отзывами о товарах") // ← ДОБАВЬ
public class ReviewController {

	private final ReviewService reviewService;

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

		ReviewResponse response = reviewService.addReview(productId, reviewCreateRequest, customUserDetail.id());

		log.info("HTTP 200 отзыв успешно добавлен ID: {}", response.id());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Operation(summary = "Отметить отзыв как полезный")
	@ApiResponse(responseCode = "200", description = "Отзыв отмечен как полезный")
	@ApiResponse(responseCode = "404", description = "Отзыв не найден")
	@ApiResponse(responseCode = "409", description = "Пользователь уже голосовал за этот отзыв")
	@PutMapping(ApiPaths.REVIEW_ID + ApiPaths.REVIEW_HELPFUL)
	public ResponseEntity<Void> markHelpful(
			@PathVariable Long reviewId,
			@AuthenticationPrincipal CustomUserDetail user) {

		reviewService.markReviewAsHelpful(reviewId,user.id());
		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "Получить отзывы товара",
			description = "Возвращает список всех отзывов для указанного товара"
	)
	@ApiResponse(responseCode = "200", description = "Список отзывов получен успешно")
	@ApiResponse(responseCode = "404", description = "Товар не найден")
	@GetMapping(ApiPaths.REVIEW_PRODUCT + ApiPaths.PRODUCT_ID)
	public ResponseEntity<List<ReviewResponse>> getProductReviews(@PathVariable Long productId) {
		log.info("HTTP GET {}{} - Получение отзывов товара ID: {}",
				ApiPaths.REVIEWS_BASE, ApiPaths.REVIEW_PRODUCT, productId);

		List<ReviewResponse> response = reviewService.getProductReviews(productId);

		log.info("HTTP 200 найдено {} отзывов для товара ID: {}", response.size(), productId);
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

		reviewService.deleteReview(reviewId, customUserDetail.id());

		log.info("HTTP 204 отзыв удален ID: {}", reviewId);
		return ResponseEntity.noContent().build();
	}

	@Operation(
			summary = "Получить отзывы пользователя",
			description = "Возвращает список всех отзывов текущего аутентифицированного пользователя"
	)
	@ApiResponse(responseCode = "200", description = "Список отзывов пользователя получен успешно")
	@ApiResponse(responseCode = "401", description = "Требуется аутентификация")
	@GetMapping(ApiPaths.MY_REVIEWS)
	public ResponseEntity<List<ReviewResponse>> getUserReviews(
			@AuthenticationPrincipal CustomUserDetail customUserDetail) {
		log.info("HTTP GET {} - Получение отзывов пользователя ID: {}",
				ApiPaths.REVIEWS_BASE, customUserDetail.id());

		List<ReviewResponse> reviewResponses = reviewService.getUserReviews(customUserDetail.id());

		log.info("HTTP 200 найдено {} отзывов пользователя", reviewResponses.size());
		return ResponseEntity.status(HttpStatus.OK).body(reviewResponses);
	}

}