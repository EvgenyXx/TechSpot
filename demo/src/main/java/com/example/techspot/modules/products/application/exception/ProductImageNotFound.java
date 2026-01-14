package com.example.techspot.modules.products.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ProductImageNotFound extends BaseException {

	public ProductImageNotFound() {
		super(
				"Изображение товара не найдено",
				HttpStatus.NOT_FOUND,
				"PRODUCT_IMAGE_NOT_FOUND"
		);
	}
}
