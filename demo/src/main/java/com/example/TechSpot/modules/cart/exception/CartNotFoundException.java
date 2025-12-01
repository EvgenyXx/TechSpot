package com.example.TechSpot.modules.cart.exception;

import com.example.TechSpot.common.exception.BaseException;
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
