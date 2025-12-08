package com.example.techspot.modules.cart.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CartNotFoundException extends BaseException {

	public CartNotFoundException(){
		super(
				"Корзина отсутствует",
				HttpStatus.NOT_FOUND,
				"CART_NOT_FOUND"
		);
	}
}
