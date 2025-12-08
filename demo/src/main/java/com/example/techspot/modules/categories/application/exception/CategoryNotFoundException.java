package com.example.techspot.modules.categories.application.exception;

import com.example.techspot.common.exception.BaseException;
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
