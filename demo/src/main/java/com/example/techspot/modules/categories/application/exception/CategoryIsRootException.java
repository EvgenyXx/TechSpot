package com.example.techspot.modules.categories.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CategoryIsRootException extends BaseException {
	public CategoryIsRootException() {
		super(
				"Категория является корневой",
				HttpStatus.BAD_REQUEST,
				"CATEGORY_IS_ROOT"
		);
	}
}
