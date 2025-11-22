package com.example.TechSpot.exception;

import org.springframework.http.HttpStatus;

public class InvalidResetCodeException extends BaseException {

	public InvalidResetCodeException(){
		super(
				"Не верный код восстановления пароля",
				HttpStatus.BAD_REQUEST,
				"INVALID_RESET_CODE"
		);
	}
}
