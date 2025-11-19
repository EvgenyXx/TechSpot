package com.example.TechSpot.exception.order;

import com.example.TechSpot.exception.BaseException;
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
