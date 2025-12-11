package com.example.techspot.modules.notification.service;

import com.example.techspot.modules.notification.event.NotificationEvent;
import com.example.techspot.modules.notification.event.PasswordResetEvent;
import com.example.techspot.modules.notification.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PasswordResetNotificationService implements NotificationStrategy {


	private final EmailSender emailSender;

	@Override
	public boolean support(NotificationEvent notificationEvent) {
		return notificationEvent instanceof PasswordResetEvent;
	}

	@Override
	public void send(NotificationEvent notificationEvent) {
		PasswordResetEvent event = (PasswordResetEvent) notificationEvent;


		Map<String, Object> vars = new HashMap<>();
		vars.put("resetCode", event.code());

		emailSender.sendEmail(
				event.email(),
				"Код сброса пароля",
				"emails/reset-password",
				vars
		);
	}
}
