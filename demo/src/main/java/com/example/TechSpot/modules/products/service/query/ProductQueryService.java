package com.example.TechSpot.modules.products.service.query;

import com.example.TechSpot.core.config.CacheNames;
import com.example.TechSpot.modules.products.dto.response.ProductResponse;
import com.example.TechSpot.modules.products.entity.ProductCategory;
import com.example.TechSpot.modules.products.exception.ProductNotFoundException;
import com.example.TechSpot.modules.products.mapper.ProductMapper;
import com.example.TechSpot.modules.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductQueryService {
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;


	@Transactional(readOnly = true)
	@Cacheable(value = CacheNames.PRODUCTS,key = "#productId")
	public ProductResponse getProduct(Long productId)  {

		log.info("Запрос товара по ID: {}", productId);

		ProductResponse response = productRepository
				.findById(productId)
				.map(product -> {
					log.debug("Товар найден: ID={}, название='{}'", productId, product.getProductName());
					return productMapper.toResponseProductWithCalculatedFields(product);
				})
				.orElseThrow(() -> {
					log.warn("Товар не найден: ID={}", productId);
					return new ProductNotFoundException();
				});

		log.info("Успешно возвращен товар: ID={}", productId);
		return response;
	}

	@Transactional(readOnly = true)
	public Page<ProductResponse> getAllProducts(Pageable pageable) {
		log.info("Запрос всех товаров. Страница: {}, размер: {}",
				pageable.getPageNumber(), pageable.getPageSize());

		Page<ProductResponse> result = productRepository
				.findAll(pageable)
				.map(product -> {
					log.trace("Маппинг товара: ID={}", product.getId());
					return productMapper.toResponseProductWithCalculatedFields(product);
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
					return productMapper.toResponseProductWithCalculatedFields(product);
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
					return productMapper.toResponseProductWithCalculatedFields(product);
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
					return productMapper.toResponseProductWithCalculatedFields(product);
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
					return productMapper.toResponseProductWithCalculatedFields(product);
				})
				.toList();

		log.info("Возвращено топ-{} товаров категории {}: {}", limit, slug, result.size());
		return result;
	}
}