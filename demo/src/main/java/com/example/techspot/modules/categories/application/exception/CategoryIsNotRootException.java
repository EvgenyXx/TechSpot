package com.example.techspot.modules.categories.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CategoryIsNotRootException extends BaseException {
	public CategoryIsNotRootException() {
		super(
				"Категория не является корневой",
				HttpStatus.BAD_REQUEST,
				"CATEGORY_NOT_ROOT"
		);
	}
}
