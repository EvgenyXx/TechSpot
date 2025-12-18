package com.example.techspot.modules.categories.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CategoryHasProductsException extends BaseException {
	public CategoryHasProductsException() {
		super(
				"Категория содержит товары",
				HttpStatus.CONFLICT,
				"CATEGORY_HAS_PRODUCTS"
		);
	}
}
