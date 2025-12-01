package com.example.TechSpot.modules.cart.exception;

import com.example.TechSpot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CartItemsNotFoundException extends BaseException {

	public CartItemsNotFoundException (){
		super(
				"Товар отсутствует в корзине ",
				HttpStatus.NOT_FOUND,
				"CART_ITEMS_NOT_FOUND"
		);
	}
}
