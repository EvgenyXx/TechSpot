package com.example.techspot.modules.categories.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CategoryHasChildrenException extends BaseException {
	public CategoryHasChildrenException() {
		super(
				"Категория содержит дочерние категории",
				HttpStatus.CONFLICT,
				"CATEGORY_HAS_CHILDREN"
		);
	}
}
