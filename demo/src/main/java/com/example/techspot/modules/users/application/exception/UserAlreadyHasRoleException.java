package com.example.techspot.modules.users.application.exception;

import com.example.techspot.common.exception.BaseException;
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
