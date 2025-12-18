package com.example.techspot.modules.reviews.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ReviewVoteNotFoundException extends BaseException {

	public ReviewVoteNotFoundException() {
		super(
				"Вы ещё не отмечали этот отзыв как полезный, нечего удалять",
				HttpStatus.NOT_FOUND,
				"REVIEW_VOTE_NOT_FOUND"
		);
	}
}
