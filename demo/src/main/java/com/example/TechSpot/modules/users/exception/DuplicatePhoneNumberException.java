package com.example.TechSpot.modules.users.exception;

import com.example.TechSpot.common.exception.BaseException;
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
