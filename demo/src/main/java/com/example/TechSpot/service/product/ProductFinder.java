package com.example.TechSpot.service.product;

import com.example.TechSpot.entity.Product;
import com.example.TechSpot.exception.product.ProductNotFoundException;
import com.example.TechSpot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductFinder {

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