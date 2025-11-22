package com.example.TechSpot.exception;

import com.example.TechSpot.entity.BaseEntity;
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
