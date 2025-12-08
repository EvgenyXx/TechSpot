package com.example.techspot.modules.orders.application.exception;

import com.example.techspot.common.exception.BaseException;
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
