package com.example.techspot.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class BaseException extends RuntimeException {

	private HttpStatus httpStatus;
	private String errorCode;
	private ZonedDateTime time;

	public BaseException(String message, HttpStatus httpStatus, String errorCode) {
		super(message);
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.time = ZonedDateTime.now();
	}
}
