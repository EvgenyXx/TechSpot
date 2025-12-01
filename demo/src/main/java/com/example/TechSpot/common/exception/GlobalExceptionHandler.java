package com.example.TechSpot.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<BusinessExceptionResponse>exceptionResponse(BaseException e){
		BusinessExceptionResponse businessExceptionResponse = new BusinessExceptionResponse(
				e.getMessage(),
				e.getErrorCode(),
				e.getHttpStatus().value(),
				e.getTime()
		);

		return new ResponseEntity<>(businessExceptionResponse,e.getHttpStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationExceptionResponse>notValidException(MethodArgumentNotValidException e){
		List<String>defaultMessage = e.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fieldError -> fieldError.getDefaultMessage())
				.toList();

		ValidationExceptionResponse businessExceptionResponse = new ValidationExceptionResponse(
				"Validation failed",
				HttpStatus.BAD_REQUEST.toString(),
				HttpStatus.BAD_REQUEST.value(),
				ZonedDateTime.now(),
				defaultMessage
		);
		return new ResponseEntity<>(businessExceptionResponse,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<BusinessExceptionResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
		BusinessExceptionResponse businessExceptionResponse = new BusinessExceptionResponse(
				"Некорректный формат данных",
				"INVALID_REQUEST_FORMAT",  // общий код, не детализируем
				HttpStatus.BAD_REQUEST.value(),
				ZonedDateTime.now()
		);

		return new ResponseEntity<>(businessExceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
