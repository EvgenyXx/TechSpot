package com.example.TechSpot.modules.products.exception;

import com.example.TechSpot.common.exception.BaseException;
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
