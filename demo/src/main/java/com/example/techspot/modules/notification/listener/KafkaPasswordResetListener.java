package com.example.techspot.modules.notification.listener;

import com.example.techspot.modules.notification.event.PasswordResetEvent;
import com.example.techspot.modules.notification.service.NotificationDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;




@Component
@RequiredArgsConstructor
public class KafkaPasswordResetListener {

	private final NotificationDispatcher notificationDispatcher;


	@KafkaListener(
			topics = "${app.kafka.topics.password-reset}",
			groupId = "password-reset"
	)

	public void onMessage(PasswordResetEvent passwordResetEvent){
		notificationDispatcher.handler(passwordResetEvent);
	}
}
