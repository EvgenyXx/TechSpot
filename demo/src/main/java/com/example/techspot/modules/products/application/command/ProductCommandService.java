package com.example.techspot.modules.products.application.command;

import com.example.techspot.core.config.CacheNames;
import com.example.techspot.modules.products.application.dto.request.ProductCreateRequest;
import com.example.techspot.modules.products.application.dto.request.ProductUpdateRequest;
import com.example.techspot.modules.products.application.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductCommandService {

	private final ProductCreateAction productCreateAction;
	private final ProductUpdateAction productUpdateAction;
	private final ProductDeleteAction productDeleteAction;

	@Transactional
	@CachePut(value = CacheNames.PRODUCTS, key = "#result.id")
	public ProductResponse create(ProductCreateRequest req, UUID userId) {
		return productCreateAction.create(req, userId);
	}

	@Transactional
	@CachePut(value = CacheNames.PRODUCTS, key = "#productId")
	public ProductResponse update(Long productId, ProductUpdateRequest req, UUID userId) {
		log.info("КЕШ ПРОДУКТА {} ОБНОВИЛСЯ",productId);
		return productUpdateAction.update(productId, req, userId);
	}

	@Transactional
	@CacheEvict(value = CacheNames.PRODUCTS, key = "#productId")
	public void delete(Long productId, UUID userId) {
		productDeleteAction.delete(productId, userId);
	}
}
