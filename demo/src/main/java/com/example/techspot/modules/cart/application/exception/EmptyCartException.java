package com.example.techspot.modules.cart.application.exception;

import com.example.techspot.common.exception.BaseException;
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
