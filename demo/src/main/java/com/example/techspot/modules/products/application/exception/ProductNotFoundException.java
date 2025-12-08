package com.example.techspot.modules.products.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BaseException {

	public ProductNotFoundException(){
		super(
				"Товар не был найден. Возможно его нет в наличии",
				HttpStatus.NOT_FOUND,
				"PRODUCT_NOT_FOUNT"
		);
	}
}
