package com.example.techspot.modules.auth.port;

import com.example.techspot.core.config.KafkaProperties;
import com.example.techspot.modules.notification.event.PasswordResetEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PasswordResetPublisherImpl implements PasswordResetPublisher {

	private final KafkaTemplate<String,PasswordResetEvent>kafkaTemplate;
	private final KafkaProperties kafkaProperties;

	@Override
	public void publishPasswordReset(PasswordResetEvent passwordResetEvent) {
		String topic = kafkaProperties.getTopics().getPasswordReset();
		kafkaTemplate.send(topic,passwordResetEvent);
	}
}
