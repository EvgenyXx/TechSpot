package com.example.techspot.modules.notification.exception;

import com.example.techspot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EmailSendException extends BaseException {

	public EmailSendException() {
		super(
				"Ошибка отправки email",
				HttpStatus.INTERNAL_SERVER_ERROR,
				"EMAIL_SEND_ERROR"
		);
	}
}
