package com.example.techspot.modules.notification;

public record PasswordResetEvent(

		String email,
		String code
) {
}
