package com.example.techspot.modules.reviews.application.helpful;

import com.example.techspot.modules.api.user.UserRepositoryProvider;
import com.example.techspot.modules.reviews.application.exception.ReviewAlreadyLikedException;
import com.example.techspot.modules.reviews.domain.entity.Review;
import com.example.techspot.modules.reviews.domain.entity.ReviewHelpfulVote;
import com.example.techspot.modules.reviews.domain.entity.ReviewHelpfulVoteId;
import com.example.techspot.modules.reviews.domain.service.ReviewFinder;
import com.example.techspot.modules.reviews.infrastructure.repository.ReviewHelpfulVoteRepository;
import com.example.techspot.modules.users.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewHelpfulMarkAction {

	private final ReviewHelpfulVoteRepository votes;
	private final ReviewFinder reviews;
	private final UserRepositoryProvider userRepositoryProvider;

	public void mark(Long reviewId, UUID userId) {

		Review review = reviews.findById(reviewId);

		boolean already = votes.existsByIdReviewIdAndIdUserId(reviewId, userId);

		if (already) {
			throw new ReviewAlreadyLikedException();
		}

		User user = userRepositoryProvider.findById(userId);

		votes.save(new ReviewHelpfulVote(
				new ReviewHelpfulVoteId(reviewId, userId),
				review,
				user
		));
	}
}
