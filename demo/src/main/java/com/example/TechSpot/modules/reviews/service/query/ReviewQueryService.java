package com.example.TechSpot.modules.reviews.service.query;


import com.example.TechSpot.modules.reviews.dto.ReviewResponse;
import com.example.TechSpot.modules.reviews.mapper.ReviewMapper;
import com.example.TechSpot.modules.reviews.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Log4j2
@RequiredArgsConstructor
public class ReviewQueryService {


	private final ReviewRepository reviewRepository;
	private final ReviewMapper reviewMapper;



	public List<ReviewResponse> getProductReviews(Long productId) {
		log.info("Получение отзывов для товара ID: {}", productId);

		List<ReviewResponse> reviews = reviewRepository.findByProductId(productId)
				.stream()
				.map(reviewMapper::toReviewResponse)
				.toList();

		log.info("Найдено {} отзывов для товара ID: {}", reviews.size(), productId);
		return reviews;
	}


	public List<ReviewResponse> getUserReviews(UUID userId) {
		log.info("Получение отзывов пользователя ID: {}", userId);

		List<ReviewResponse> reviews = reviewRepository.findByUserId(userId)
				.stream()
				.map(reviewMapper::toReviewResponse)
				.toList();

		log.info("Найдено {} отзывов пользователя ID: {}", reviews.size(), userId);
		return reviews;
	}

}
