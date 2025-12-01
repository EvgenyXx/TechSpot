package com.example.TechSpot.modules.cart.exception;

import com.example.TechSpot.common.exception.BaseException;
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
