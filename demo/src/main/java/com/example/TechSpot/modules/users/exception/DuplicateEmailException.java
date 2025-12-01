package com.example.TechSpot.modules.users.exception;

import com.example.TechSpot.common.exception.BaseException;
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
