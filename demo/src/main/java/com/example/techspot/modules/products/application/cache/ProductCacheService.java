package com.example.techspot.modules.products.application.cache;

import com.example.techspot.core.config.CacheNames;
import com.example.techspot.modules.products.application.dto.response.ProductCacheModel;
import com.example.techspot.modules.products.application.exception.ProductNotFoundException;
import com.example.techspot.modules.products.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductCacheService {
	private final ProductRepository productRepository;





	@Cacheable(value = CacheNames.PRODUCTS, key = "#id")
	@Transactional(readOnly = true)
	public ProductCacheModel get(Long id) {
		log.info("🔥 Взято из кеша productId=" + id);
		return productRepository.findById(id)
				.map(p -> new ProductCacheModel(
						p.getId(),
						p.getProductName(),
						p.getPrice(),
						p.getQuantity(),
						p.getDescription(),
						p.getCategory().getSlug(),
						p.getUser().getFirstname(),
						p.getUser().getEmail(),
						p.getCreatedAt(),
						p.getUpdatedAt(),
						p.isAvailable(),
						p.getCategory().getName()
				))
				.orElseThrow(ProductNotFoundException::new);
	}
}

