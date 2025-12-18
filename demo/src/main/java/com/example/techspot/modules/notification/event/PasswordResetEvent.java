package com.example.techspot.modules.notification.event;

public record PasswordResetEvent(

		String email,
		String code

) implements NotificationEvent{
	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public String getType() {
		return "PASSWORD_RESET";
	}
}
