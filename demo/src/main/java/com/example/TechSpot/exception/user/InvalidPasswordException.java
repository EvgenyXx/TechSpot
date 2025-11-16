package com.example.TechSpot.exception.user;

import com.example.TechSpot.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends BaseException {
	private static final String ERROR_CODE = "INVALID_PASSWORD";

	public InvalidPasswordException(){
		super(
				"Пароль не верный. Проверьте правильность пароля",
				HttpStatus.UNAUTHORIZED,
				ERROR_CODE
		);
	}
}
