package com.example.TechSpot.controller.product;


import com.example.TechSpot.dto.product.ProductResponse;
import com.example.TechSpot.entity.ProductCategory;
import com.example.TechSpot.security.CustomUserDetail;
import com.example.TechSpot.service.product.ProductQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/query")
@Log4j2
@RequiredArgsConstructor
public class ProductQueryController {

	private final ProductQueryService productQueryService;


	@GetMapping("/product{id}")
	public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
		log.info("GET /api/v1/products/{} - Получение товара", id);
		ProductResponse response = productQueryService.getProduct(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Page<ProductResponse>> getAllProducts(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "12") int size) {

		log.info("GET /api/v1/products?page={}&size={} - Все товары", page, size);
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductResponse> products = productQueryService.getAllProducts(pageable);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<ProductResponse>> searchProducts(
			@RequestParam String query,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "12") int size) {

		log.info("GET /api/v1/products/search?query={} - Поиск товаров", query);
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductResponse> products = productQueryService.searchProducts(query, pageable);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/my-products")
	public ResponseEntity<List<ProductResponse>> getMyProducts(
			@AuthenticationPrincipal CustomUserDetail customUserDetail) {

		log.info("GET /api/v1/products/my-products - Мои товары");
		List<ProductResponse> products = productQueryService.getMyProducts(customUserDetail.id());
		return ResponseEntity.ok(products);
	}

	@GetMapping("/top/{category}")
	public ResponseEntity<List<ProductResponse>> getTopProductsByCategory(
			@PathVariable ProductCategory category,
			@RequestParam(defaultValue = "3") int limit) {

		log.info("GET /api/v1/products/top/{}?limit={} - Топ товары по категории", category, limit);
		List<ProductResponse> products = productQueryService.getTopProductsByCategory(category, limit);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/category/{category}")
	public ResponseEntity<Page<ProductResponse>> getProductsByCategory(
			@PathVariable ProductCategory category,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "12") int size) {

		log.info("GET /api/v1/products/category/{} - Фильтрация по категории", category);
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductResponse> products = productQueryService.filterProductsByCategory(category, pageable);
		return ResponseEntity.ok(products);
	}











}
