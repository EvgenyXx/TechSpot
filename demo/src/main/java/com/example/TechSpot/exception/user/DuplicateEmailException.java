package com.example.TechSpot.exception.user;

import com.example.TechSpot.exception.BaseException;
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
