package com.example.techspot.modules.products.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InsufficientStockException extends BaseException {

	public InsufficientStockException(){
		super(
				"Товара не достаточно на складе ",
				HttpStatus.CONFLICT,
				"INSUFFICIENT_STOCK"
		);
	}
}
