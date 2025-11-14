package com.example.TechSpot.exception.product;

import com.example.TechSpot.exception.BaseException;
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
