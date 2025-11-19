package com.example.TechSpot.exception.review;

import com.example.TechSpot.exception.BaseException;
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
