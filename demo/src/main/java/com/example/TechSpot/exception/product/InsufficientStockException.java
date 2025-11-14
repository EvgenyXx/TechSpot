package com.example.TechSpot.exception.product;

import com.example.TechSpot.exception.BaseException;
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
