package com.example.techspot.modules.products.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ProductImageLimitExceededException extends BaseException {

	public ProductImageLimitExceededException() {
		super(
				"Превышен лимит изображений для товара",
				HttpStatus.CONFLICT,
				"PRODUCT_IMAGE_LIMIT_EXCEEDED"
		);
	}
}
