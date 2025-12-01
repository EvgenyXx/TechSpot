package com.example.TechSpot.common.exception;

import java.time.ZonedDateTime;
import java.util.List;

public record ValidationExceptionResponse(
		String message,
		String errorCode,
		int httpStatus,
		ZonedDateTime time,
		List<String> errors  // ← есть список ошибок
) {
}
