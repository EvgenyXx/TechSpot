package com.example.TechSpot.exception;


import org.springframework.http.HttpStatus;


public class CategoryAlreadyExistsException extends BaseException {

	public CategoryAlreadyExistsException(){
		super(
				"Категория с таким названием уже существует в этой ветке",
				HttpStatus.CONFLICT,
				"CATEGORY_ALREADY"
		);
	}
}
