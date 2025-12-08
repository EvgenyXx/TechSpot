package com.example.techspot.modules.products.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;



public class ProductAccessDeniedException extends BaseException {

	public ProductAccessDeniedException(){
		super(
				"У вас нет прав для удаления данного товара ",
				HttpStatus.FORBIDDEN,
				"PRODUCT_ACCESS_DENIED"
		);
	}
}
