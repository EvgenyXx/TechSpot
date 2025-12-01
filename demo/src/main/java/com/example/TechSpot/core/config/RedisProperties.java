package com.example.TechSpot.core.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;


@Data
@Component
@ConfigurationProperties(prefix = "app.redis")
public class RedisProperties {

	private final Login login = new Login();



	@Data
	public static class Login {
		private String loginAttemptsPrefix;
		private Duration loginAttemptsTtl;
		private Long attemptsFailed;
	}
}