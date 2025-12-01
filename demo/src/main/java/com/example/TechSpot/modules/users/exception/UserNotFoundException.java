package com.example.TechSpot.modules.users.exception;

import com.example.TechSpot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

	public UserNotFoundException  (){
		super(
				"Пользователь не найден",
				HttpStatus.NOT_FOUND,
				"USER_NOT_FOUND"
		);
	}
}
