package com.example.techspot.modules.products.domain.service.command;


import com.example.techspot.core.config.CacheNames;
import com.example.techspot.modules.products.application.exception.InsufficientStockException;
import com.example.techspot.modules.products.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductStockService {



	private final ProductRepository productRepository;

	@CacheEvict(value = CacheNames.PRODUCTS,key = "#productId")
	public void reduceQuantity(Long productId, int quantity) {
		int updatedRows = productRepository.reduceQuantity(productId, quantity);
		if (updatedRows == 0 ){
			throw new InsufficientStockException();
		}
	}
}
