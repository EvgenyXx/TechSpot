package com.example.TechSpot.modules.products.service.validation;


import com.example.TechSpot.modules.products.exception.DuplicateProductForUser;
import com.example.TechSpot.modules.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductValidationService {
	private final ProductRepository productRepository;

	public void validateProductUniqueForUser(String productName, UUID userId) {
		log.debug("Проверка уникальности товара: name='{}', userId={}", productName, userId);

		if (productRepository.existsByProductNameAndUserId(productName, userId)) {
			log.warn("Нарушение уникальности товара: name='{}' уже существует у пользователя {}",
					productName, userId);
			throw new DuplicateProductForUser();
		}

		log.debug("Проверка уникальности пройдена: name='{}'", productName);
	}
}
