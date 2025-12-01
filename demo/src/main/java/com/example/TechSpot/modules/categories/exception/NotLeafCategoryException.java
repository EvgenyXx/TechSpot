package com.example.TechSpot.modules.categories.exception;

import com.example.TechSpot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class NotLeafCategoryException extends BaseException {

	public NotLeafCategoryException(){
		super(
				"Категория не является конечной. Выберите подкатегорию для создания товара.",
				HttpStatus.BAD_REQUEST,
				"NOT_LEAF_CATEGORY"
		);
	}
}
