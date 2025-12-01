package com.example.TechSpot.modules.categories.exception;


import com.example.TechSpot.common.exception.BaseException;
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
