package com.example.TechSpot.modules.reviews.exception;

import com.example.TechSpot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ReviewNotFoundException extends BaseException {

	public ReviewNotFoundException(){
		super(
				"Такой отзыв уже не существует",
				HttpStatus.NOT_FOUND,
				"REVIEW_NOT_FOUND"
		);
	}
}
