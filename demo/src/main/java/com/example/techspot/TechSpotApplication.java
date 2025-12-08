package com.example.techspot;

import com.example.techspot.core.config.RedisProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableConfigurationProperties(RedisProperties.class)
public class TechSpotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechSpotApplication.class, args);
	}

}
