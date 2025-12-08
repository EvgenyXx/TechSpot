package com.example.techspot.modules.reviews.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ReviewAlreadyLikedException extends BaseException {


	public ReviewAlreadyLikedException(){
		super(
				"Вы больше не можете отметить отзыв как полезный ",
				HttpStatus.CONFLICT,
				"REVIEW_ALREADY_LIKED"
		);
	}
}
