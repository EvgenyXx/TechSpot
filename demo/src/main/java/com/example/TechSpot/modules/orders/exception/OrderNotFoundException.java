package com.example.TechSpot.modules.orders.exception;

import com.example.TechSpot.common.exception.BaseException;
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
