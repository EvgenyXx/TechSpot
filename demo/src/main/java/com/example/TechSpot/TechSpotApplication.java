package com.example.TechSpot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TechSpotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechSpotApplication.class, args);
	}

}
