package com.example.TechSpot.modules.reviews.service.validation;

import com.example.TechSpot.modules.reviews.exception.DuplicateReviewException;
import com.example.TechSpot.modules.reviews.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReviewValidationService {

	private final ReviewRepository reviewRepository;

	public void checkUserHasNotVoted(Long reviewId, UUID userId) {
		log.info("Проверка голоса пользователя {} для отзыва {}", userId, reviewId);

		if (reviewRepository.existsByIdAndUserId(reviewId, userId)) {
			log.warn("Пользователь {} уже голосовал за отзыв {}", userId, reviewId);
			throw new DuplicateReviewException();
		}

		log.debug("Пользователь {} еще не голосовал за отзыв {}", userId, reviewId);
	}

	public void  checkingReviewDuplicateForUserOnProduct(UUID userId,Long productId){
		log.info("Проверка отзыва пользователя {} на товар {}",userId,productId);
		if (reviewRepository.existsByProductIdAndUserId(productId,userId)){
			log.warn("Попытка оставить отзыв на товар где уже есть отзыв {}",productId);
			throw new DuplicateReviewException();
		}
	}
}