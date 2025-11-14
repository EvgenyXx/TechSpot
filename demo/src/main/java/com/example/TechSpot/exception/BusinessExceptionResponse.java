package com.example.TechSpot.exception;

import java.time.ZonedDateTime;
import java.util.List;

public record BusinessExceptionResponse(
		String message,
		String errorCode,
		int httpStatus,
		ZonedDateTime time
) {
}
