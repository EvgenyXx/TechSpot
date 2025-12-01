package com.example.TechSpot.modules.reviews.controller;


import com.example.TechSpot.common.constants.ApiPaths;
import com.example.TechSpot.core.security.CustomUserDetail;
import com.example.TechSpot.modules.reviews.dto.ReviewResponse;
import com.example.TechSpot.modules.reviews.service.query.ReviewQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@RequestMapping(ApiPaths.REVIEWS_BASE)
@RestController
@Tag(name = "Reviews", description = "API для запросов отзывов о товарах")
public class QueryReviewController {


	private  final ReviewQueryService reviewQueryService;

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

		List<ReviewResponse> response = reviewQueryService.getProductReviews(productId);

		log.info("HTTP 200 найдено {} отзывов для товара ID: {}", response.size(), productId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
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

		List<ReviewResponse> reviewResponses = reviewQueryService.getUserReviews(customUserDetail.id());

		log.info("HTTP 200 найдено {} отзывов пользователя", reviewResponses.size());
		return ResponseEntity.status(HttpStatus.OK).body(reviewResponses);
	}
}
