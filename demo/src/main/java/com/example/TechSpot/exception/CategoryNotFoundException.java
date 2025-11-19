package com.example.TechSpot.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends BaseException {

	public CategoryNotFoundException(){
		super(
				"Категория не найдена",
				HttpStatus.NOT_FOUND,
				"CATEGORY_NOT_FOUND"
		);
	}
}
