package com.example.techspot.modules.reviews.application.helpful;

import com.example.techspot.modules.reviews.application.exception.ReviewVoteNotFoundException;
import com.example.techspot.modules.reviews.infrastructure.repository.ReviewHelpfulVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewHelpfulUnmarkAction {

	private final ReviewHelpfulVoteRepository votes;

	public void unmark(Long reviewId, UUID userId) {
		int removed = votes.deleteByIdReviewIdAndIdUserId(reviewId, userId);

		if (removed == 0) {
			throw new ReviewVoteNotFoundException();

		}
	}
}
