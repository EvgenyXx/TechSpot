package com.example.TechSpot.exception;

import org.springframework.http.HttpStatus;

public class ReviewAccessDeniedException extends BaseException {

	public ReviewAccessDeniedException(){
		super(
				"У вас нет прав для удаления данного отзыва",
				HttpStatus.LOCKED,
				"REVIEW_ACCESS_DENIED"
		);
	}
}
