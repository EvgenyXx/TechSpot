package com.example.techspot.core.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("TechSpot API")
						.version("1.0")
						.description("""
                    REST API для интернет-магазина TechSpot.
                    
                    ## Основные возможности:
                    - Управление пользователями (регистрация, аутентификация)
                    - Каталог товаров с поиском и фильтрацией
                    - Корзина покупок и оформление заказов
                    - Управление категориями товаров
                    """)
						.contact(new Contact()
								.name("TechSpot Development Team")
								.email("dev@techspot.com")
								.url("https://techspot.com"))
						.license(new License()
								.name("Proprietary")
								.url("https://techspot.com/license")))
				.externalDocs(new ExternalDocumentation()
						.description("Полная документация TechSpot")
						.url("https://docs.techspot.com"));
	}
}