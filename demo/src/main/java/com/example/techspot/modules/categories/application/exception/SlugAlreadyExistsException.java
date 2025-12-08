package com.example.techspot.modules.categories.application.exception;

import com.example.techspot.common.exception.BaseException;
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
