package com.example.TechSpot.exception.cart;

import com.example.TechSpot.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EmptyCartException extends BaseException {

	public EmptyCartException (){
		super(
				"Нельзя создать заказ с пустой корзиной ",
				HttpStatus.BAD_REQUEST,
				"EMPTY_CART"
		);
	}
}
