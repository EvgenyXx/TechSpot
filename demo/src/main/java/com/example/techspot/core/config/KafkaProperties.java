package com.example.techspot.core.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.kafka")
public class KafkaProperties {
	private Topics topics;

	@Data
	public static class Topics {
		private String orderCreated;
		private String orderUpdated;
		private String passwordReset;
	}
}
