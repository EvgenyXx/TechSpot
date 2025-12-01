package com.example.TechSpot.modules.notification;

public record PasswordResetEvent(

		String email,
		String code
) {
}
