package com.example.TechSpot.exception.cart;

import com.example.TechSpot.exception.BaseException;
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
