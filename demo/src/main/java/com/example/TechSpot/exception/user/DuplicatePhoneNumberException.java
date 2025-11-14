package com.example.TechSpot.exception.user;

import com.example.TechSpot.exception.BaseException;
import org.springframework.http.HttpStatus;

public class DuplicatePhoneNumberException extends BaseException {

	public DuplicatePhoneNumberException(){
		super(
				"Данный номер телефона уже используется. Попробуйте ввести другой ",
				HttpStatus.CONFLICT,
				"DUPLICATE_PHONE_NUMBER"
		);
	}
}
