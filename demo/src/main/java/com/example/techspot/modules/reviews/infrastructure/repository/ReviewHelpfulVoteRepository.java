package com.example.techspot.modules.reviews.infrastructure.repository;


import com.example.techspot.modules.reviews.domain.entity.ReviewHelpfulVote;
import com.example.techspot.modules.reviews.domain.entity.ReviewHelpfulVoteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewHelpfulVoteRepository
		extends JpaRepository<ReviewHelpfulVote, ReviewHelpfulVoteId> {

	boolean existsByIdReviewIdAndIdUserId(Long reviewId, UUID userId);

	int deleteByIdReviewIdAndIdUserId(Long reviewId, UUID userId);

	long countByIdReviewId(Long reviewId);

	int countByReviewId(Long id);
}
