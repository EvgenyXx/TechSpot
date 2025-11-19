package com.example.TechSpot.exception.review;

import com.example.TechSpot.exception.BaseException;
import org.springframework.http.HttpStatus;

public class DuplicateReviewException extends BaseException {

	public DuplicateReviewException(){
		super(
				"Нельзя оставлять больше одного отзыва на товар",
				HttpStatus.CONFLICT,
				"DUPLICATE_REVIEW"
		);
	}
}
