package com.example.TechSpot.modules.reviews.exception;

import com.example.TechSpot.common.exception.BaseException;
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
