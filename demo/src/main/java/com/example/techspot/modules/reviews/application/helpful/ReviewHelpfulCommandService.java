package com.example.techspot.modules.reviews.application.helpful;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewHelpfulCommandService {

	private final ReviewHelpfulMarkAction markAction;
	private final ReviewHelpfulUnmarkAction unmarkAction;

	public void markHelpful(Long reviewId, UUID userId) {
		markAction.mark(reviewId, userId);
	}

	public void unmarkHelpful(Long reviewId, UUID userId) {
		unmarkAction.unmark(reviewId, userId);
	}
}
