package com.example.techspot.modules.products.domain.service.query;

import com.example.techspot.modules.products.domain.entity.Product;
import com.example.techspot.modules.products.application.exception.ProductNotFoundException;
import com.example.techspot.modules.products.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductEntityQueryService {

	private final ProductRepository productRepository;

	public Product findById(Long productId) {
		log.info("Поиск товара по ID: {}", productId);

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> {
					log.warn("Товар с ID {} не найден", productId);
					return new ProductNotFoundException();
				});

		log.info("Товар с ID {} успешно найден: {}", productId, product.getProductName());
		return product;
	}
}
