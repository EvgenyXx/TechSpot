package com.example.TechSpot.modules.categories.exception;

import com.example.TechSpot.common.exception.BaseException;
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
