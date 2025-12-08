package com.example.techspot.modules.reviews.application.command;


import com.example.techspot.core.config.CacheNames;
import com.example.techspot.modules.reviews.application.command.action.ReviewCreationAction;
import com.example.techspot.modules.reviews.application.command.action.ReviewDeleteAction;
import com.example.techspot.modules.reviews.application.dto.ReviewCreateRequest;
import com.example.techspot.modules.reviews.application.dto.ReviewResponse;
import com.example.techspot.modules.reviews.application.factory.ReviewResponseFactory;
import com.example.techspot.modules.reviews.domain.entity.Review;
import com.example.techspot.modules.reviews.application.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandService {

	private final ReviewCreationAction createAction;
	private final ReviewDeleteAction deleteAction;
	private final ReviewResponseFactory reviewResponseFactory;

	@CachePut(value = CacheNames.REVIEWS, key = "#result.id")
	public ReviewResponse addReview(Long productId, ReviewCreateRequest req, UUID userId) {
		Review review = createAction.create(productId, userId, req);
		return reviewResponseFactory.from(review,userId);
	}



	@CacheEvict(value = CacheNames.REVIEWS, key = "#reviewId")
	public void deleteReview(Long reviewId, UUID userId) {
		deleteAction.delete(reviewId, userId);
	}
}
