package com.example.techspot.modules.products.application.query;

import com.example.techspot.core.config.CacheNames;
import com.example.techspot.modules.discount.domain.service.DiscountService;
import com.example.techspot.modules.products.application.cache.ProductCacheService;
import com.example.techspot.modules.products.application.dto.response.ProductCacheModel;
import com.example.techspot.modules.products.application.dto.response.ProductResponse;
import com.example.techspot.modules.products.application.exception.ProductNotFoundException;
import com.example.techspot.modules.products.application.factory.ProductFactory;
import com.example.techspot.modules.products.domain.entity.Product;
import com.example.techspot.modules.products.domain.service.validation.ProductPricingService;
import com.example.techspot.modules.products.infrastructure.repository.ProductRepository;
import com.example.techspot.modules.products.application.factory.ProductResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductQueryService {
	private final ProductRepository productRepository;
	private final ProductResponseFactory productResponseFactory;

	private final ProductCacheService cache;
	private final ProductFactory productFactory;
	private final ProductPricingService pricing;
	private final ProductResponseFactory responseFactory;


	public ProductResponse getProduct(Long id) {


		ProductCacheModel cached = cache.get(id);


		Product product = productFactory.fromCache(cached);


		BigDecimal discount = pricing.calculateFor(product);


		return responseFactory.build(cached, discount);

	}




	@Transactional(readOnly = true)
	public Page<ProductResponse> getAllProducts(Pageable pageable) {
		log.info("Запрос всех товаров. Страница: {}, размер: {}",
				pageable.getPageNumber(), pageable.getPageSize());

		Page<ProductResponse> result = productRepository
				.findAll(pageable)
				.map(product -> {
					log.trace("Маппинг товара: ID={}", product.getId());
					return productResponseFactory.fromEntity(product);
				});

		log.info("Возвращено товаров: {} из {}", result.getNumberOfElements(), result.getTotalElements());
		return result;
	}

	@Transactional(readOnly = true)
	public Page<ProductResponse> searchProducts(String query, Pageable pageable) {
		log.info("Поиск товаров по запросу: '{}'. Страница: {}, размер: {}",
				query, pageable.getPageNumber(), pageable.getPageSize());

		Page<ProductResponse> result = productRepository
				.searchProduct(query, query, pageable)
				.map(product -> {
					log.debug("Найден товар по запросу '{}': ID={}, название='{}'",
							query, product.getId(), product.getProductName());
					return productResponseFactory.fromEntity(product);
				});

		log.info("Поиск завершен. Найдено товаров: {} из {}",
				result.getNumberOfElements(), result.getTotalElements());
		return result;
	}

	@Transactional(readOnly = true)
	public Page<ProductResponse> filterProductsByCategory(String slug, Pageable pageable) {
		log.info("Фильтрация товаров по категории: {}. Страница: {}, размер: {}",
				slug, pageable.getPageNumber(), pageable.getPageSize());

		Page<ProductResponse> result = productRepository
				.findByCategorySlug(slug, pageable)
				.map(product -> {
					log.trace("Товар категории {}: ID={}, название='{}'",
							slug, product.getId(), product.getProductName());
					return productResponseFactory.fromEntity(product);
				});

		log.info("Фильтрация завершена. Найдено товаров в категории {}: {} из {}",
				slug, result.getNumberOfElements(), result.getTotalElements());
		return result;
	}

	@Transactional(readOnly = true)
	public List<ProductResponse> getMyProducts(UUID customerId) {
		log.info("Запрос товаров пользователя: ID={}", customerId);

		List<ProductResponse> result = productRepository
				.findByUserId(customerId)
				.stream()
				.map(product -> {
					log.debug("Товар пользователя {}: ID={}, название='{}'",
							customerId, product.getId(), product.getProductName());
					return productResponseFactory.fromEntity(product);
				})
				.toList();

		log.info("Возвращено товаров пользователя {}: {}", customerId, result.size());
		return result;
	}

	@Transactional(readOnly = true)
	public List<ProductResponse> getTopProductsByCategory(String slug, int limit) {
		log.info("Запрос топ-{} товаров в категории: {}", limit, slug);

		Pageable pageable = PageRequest.of(0, limit);

		List<ProductResponse> result = productRepository
				.findTopByCategorySlugOrderByPriceDesc(slug, pageable)
				.stream()
				.map(product -> {
					log.debug("Топ товар категории {}: ID={}, название='{}', цена={}",
							slug, product.getId(), product.getProductName(), product.getPrice());
					return productResponseFactory.fromEntity(product);
				})
				.toList();

		log.info("Возвращено топ-{} товаров категории {}: {}", limit, slug, result.size());
		return result;
	}
}