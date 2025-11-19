package com.example.TechSpot.exception.category;

import com.example.TechSpot.exception.BaseException;
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
