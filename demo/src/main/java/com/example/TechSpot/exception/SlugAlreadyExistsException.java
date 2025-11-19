package com.example.TechSpot.exception;

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
