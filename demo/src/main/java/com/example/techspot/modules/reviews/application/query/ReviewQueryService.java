package com.example.techspot.modules.reviews.application.query;


import com.example.techspot.modules.reviews.application.dto.ReviewResponse;
import com.example.techspot.modules.reviews.application.factory.ReviewResponseFactory;
import com.example.techspot.modules.reviews.application.mapper.ReviewMapper;
import com.example.techspot.modules.reviews.infrastructure.repository.ReviewRepository;
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
	private final ReviewResponseFactory reviewResponseFactory;



	public List<ReviewResponse> getProductReviews(Long productId,UUID userId) {
		log.info("Получение отзывов для товара ID: {}", productId);

		List<ReviewResponse> reviews = reviewRepository.findByProductId(productId)
				.stream()
				.map(review -> reviewResponseFactory.from(review,userId))
				.toList();

		log.info("Найдено {} отзывов для товара ID: {}", reviews.size(), productId);
		return reviews;
	}


	public List<ReviewResponse> getUserReviews(UUID userId) {
		log.info("Получение отзывов пользователя ID: {}", userId);

		List<ReviewResponse> reviews = reviewRepository.findByUserId(userId)
				.stream()
				.map(review -> reviewResponseFactory.from(review,userId))
				.toList();

		log.info("Найдено {} отзывов пользователя ID: {}", reviews.size(), userId);
		return reviews;
	}

}
