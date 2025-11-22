package com.example.TechSpot.exception;

import org.springframework.http.HttpStatus;

public class ResetCodeExpiredException extends BaseException {

	public ResetCodeExpiredException(){
		super(
				"Время действия кода сброса пароля истекло",
				HttpStatus.BAD_REQUEST,
				"RESET_CODE_EXPIRED"
		);
	}
}