package com.example.TechSpot.exception.review;

import com.example.TechSpot.exception.BaseException;
import org.springframework.http.HttpStatus;

public class SlugAlreadyExistsException extends BaseException {

	public SlugAlreadyExistsException(){
		super(
				"Категория с таким slug уже существует в этой ветке",
				HttpStatus.CONFLICT,
				"SLUG_ALREADY"
		);
	}
}
