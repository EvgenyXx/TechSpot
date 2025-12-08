package com.example.techspot.modules.reviews.api;

import com.example.techspot.common.constants.ApiPaths;
import com.example.techspot.core.security.CustomUserDetail;
import com.example.techspot.modules.reviews.application.helpful.ReviewHelpfulCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.REVIEWS_BASE)
@RequiredArgsConstructor
@Tag(name = "Review Helpful", description = "API для лайков/полезности отзывов")
public class ReviewHelpfulController {

	private final ReviewHelpfulCommandService helpfulService;

	@Operation(
			summary = "Отметить отзыв как полезный",
			description = """
                    Помечает отзыв как полезный от имени текущего пользователя.
                    - Пользователь может отметить отзыв только один раз.
                    - При повторной попытке будет ошибка.
                    """
	)
	@ApiResponse(responseCode = "200", description = "Отзыв отмечен как полезный")
	@ApiResponse(responseCode = "404", description = "Отзыв не найден")
	@ApiResponse(responseCode = "409", description = "Пользователь уже отмечал этот отзыв")
	@PostMapping(ApiPaths.REVIEW_ID + ApiPaths.REVIEW_HELPFUL)
	public ResponseEntity<Void> markHelpful(
			@PathVariable Long reviewId,
			@AuthenticationPrincipal CustomUserDetail user
	) {
		helpfulService.markHelpful(reviewId, user.id());
		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "Убрать отметку полезности",
			description = """
                    Убирает отметку полезности.
                    - Пользователь может убрать только свою отметку.
                    """
	)
	@ApiResponse(responseCode = "204", description = "Отметка успешно удалена")
	@ApiResponse(responseCode = "404", description = "Пользователь не отмечал этот отзыв")
	@DeleteMapping(ApiPaths.REVIEW_ID + ApiPaths.REVIEW_HELPFUL)
	public ResponseEntity<Void> unmarkHelpful(
			@PathVariable Long reviewId,
			@AuthenticationPrincipal CustomUserDetail user
	) {
		helpfulService.unmarkHelpful(reviewId, user.id());
		return ResponseEntity.noContent().build();
	}
}
