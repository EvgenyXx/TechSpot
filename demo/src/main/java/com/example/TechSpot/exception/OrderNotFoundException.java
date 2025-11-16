package com.example.TechSpot.exception;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BaseException {

	public OrderNotFoundException (){
		super(
				"Заказ не был найден ",
				HttpStatus.NOT_FOUND,
				"ORDER_NOT_FOUND"
		);
	}
}
