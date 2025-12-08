package com.example.techspot.modules.users.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends BaseException {

	public DuplicateEmailException(){
		super(
				"Данный электронный адрес уже используется.Попробуйте другой",
				HttpStatus.CONFLICT,
				"DUPLICATE_EMAIL"
		);
	}
}
