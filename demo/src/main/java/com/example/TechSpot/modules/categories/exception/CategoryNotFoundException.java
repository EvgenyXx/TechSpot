package com.example.TechSpot.modules.categories.exception;

import com.example.TechSpot.common.exception.BaseException;
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
