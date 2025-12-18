package com.example.techspot.modules.auth.exception;

import com.example.techspot.common.exception.BaseException;
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
