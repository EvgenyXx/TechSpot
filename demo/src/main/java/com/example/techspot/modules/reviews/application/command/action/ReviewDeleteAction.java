package com.example.techspot.modules.reviews.application.command.action;

import com.example.techspot.modules.reviews.application.exception.ReviewAccessDeniedException;
import com.example.techspot.modules.reviews.domain.entity.Review;
import com.example.techspot.modules.reviews.domain.service.ReviewFinder;
import com.example.techspot.modules.reviews.infrastructure.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewDeleteAction {

	private final ReviewFinder finder;
	private final ReviewRepository reviews;

	public void delete(Long reviewId, UUID userId) {
		Review review = finder.findById(reviewId);

		if (!review.getUser().getId().equals(userId)) {
			throw new ReviewAccessDeniedException();
		}

		reviews.delete(review);
	}
}
