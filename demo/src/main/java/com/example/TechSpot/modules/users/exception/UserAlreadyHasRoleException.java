package com.example.TechSpot.modules.users.exception;

import com.example.TechSpot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserAlreadyHasRoleException extends BaseException {

	public UserAlreadyHasRoleException(){
		super(
				"Данная роль уже есть у пользователя",
				HttpStatus.CONFLICT,
				"USER_ALREADY_HAS_ROLE"
		);
	}
}
