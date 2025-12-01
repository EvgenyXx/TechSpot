package com.example.TechSpot.modules.users.exception;

import com.example.TechSpot.common.exception.BaseException;
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
