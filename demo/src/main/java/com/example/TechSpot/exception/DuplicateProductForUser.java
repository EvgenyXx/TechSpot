package com.example.TechSpot.exception;

import org.springframework.http.HttpStatus;

public class DuplicateProductForUser extends BaseException {

	public DuplicateProductForUser() {  // ← УБРАТЬ private!
		super(
				"Товар с таким названием уже существует у пользователя",  // ← Добавить сообщение
				HttpStatus.CONFLICT,  // ← Добавить HttpStatus
				"DUPLICATE_PRODUCT_FOR_USER"  // ← Исправить опечатку FRO → FOR
		);
	}
}