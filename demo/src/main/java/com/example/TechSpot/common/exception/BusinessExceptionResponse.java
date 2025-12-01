package com.example.TechSpot.common.exception;

import java.time.ZonedDateTime;

public record BusinessExceptionResponse(
		String message,
		String errorCode,
		int httpStatus,
		ZonedDateTime time
) {
}
