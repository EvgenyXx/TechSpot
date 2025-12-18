package com.example.techspot.modules.reviews.application.factory;

import com.example.techspot.modules.reviews.application.dto.ReviewResponse;
import com.example.techspot.modules.reviews.application.mapper.ReviewMapper;
import com.example.techspot.modules.reviews.domain.entity.Review;
import com.example.techspot.modules.reviews.infrastructure.repository.ReviewHelpfulVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewResponseFactory {

	private final ReviewMapper mapper;
	private final ReviewHelpfulVoteRepository helpfulRepo;

	public ReviewResponse from(Review review, UUID currentUserId) {
		int helpfulCount = helpfulRepo.countByReviewId(review.getId());
		boolean isLike = helpfulRepo.existsByIdReviewIdAndIdUserId(review.getId(), currentUserId);



		return new ReviewResponse(
				review.getId(),
				review.getUser().getId(),
				review.getUser().getFirstname() + " " + review.getUser().getLastname(),
				review.getRating(),
				review.getComment(),
				helpfulCount,
				review.getCreatedAt(),
				isLike
		);
	}
}
