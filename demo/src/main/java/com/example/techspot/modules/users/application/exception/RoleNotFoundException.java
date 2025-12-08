package com.example.techspot.modules.users.application.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends BaseException {

	public RoleNotFoundException(){
		super(
				"Роль не найдена",
				HttpStatus.NOT_FOUND,
				"ROLE_NOT_FOUND"
		);
	}
}
