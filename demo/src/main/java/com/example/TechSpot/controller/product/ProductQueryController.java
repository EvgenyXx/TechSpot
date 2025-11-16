package com.example.TechSpot.controller.product;


import com.example.TechSpot.constants.ApiPaths;
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
@RequestMapping(ApiPaths.QUERY_BASE)
@Log4j2
@RequiredArgsConstructor
public class ProductQueryController {

	private final ProductQueryService productQueryService;


	@GetMapping(ApiPaths.PRODUCT_ID)
	public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
		log.info("GET {}{} - Получение товара", ApiPaths.QUERY_BASE, ApiPaths.PRODUCT_ID);
		ProductResponse response = productQueryService.getProduct(productId);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Page<ProductResponse>> getAllProducts(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "12") int size) {
		log.info("GET {}?page={}&size={} - Все товары", ApiPaths.QUERY_BASE, page, size);
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductResponse> products = productQueryService.getAllProducts(pageable);
		return ResponseEntity.ok(products);
	}

	@GetMapping(ApiPaths.SEARCH)
	public ResponseEntity<Page<ProductResponse>> searchProducts(
			@RequestParam String query,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "12") int size) {
		log.info("GET {}{}?query={} - Поиск товаров", ApiPaths.QUERY_BASE, ApiPaths.SEARCH, query);
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductResponse> products = productQueryService.searchProducts(query, pageable);
		return ResponseEntity.ok(products);
	}

	@GetMapping(ApiPaths.GET_MY_PRODUCTS)
	public ResponseEntity<List<ProductResponse>> getMyProducts(
			@AuthenticationPrincipal CustomUserDetail customUserDetail) {
		log.info("GET {}{} - Мои товары", ApiPaths.QUERY_BASE, ApiPaths.GET_MY_PRODUCTS);
		List<ProductResponse> products = productQueryService.getMyProducts(customUserDetail.id());
		return ResponseEntity.ok(products);
	}

	@GetMapping(ApiPaths.TOP_PRODUCTS + ApiPaths.CATEGORY_NAME)
	public ResponseEntity<List<ProductResponse>> getTopProductsByCategory(
			@PathVariable ProductCategory category,
			@RequestParam(defaultValue = "3") int limit) {
		log.info("GET {}{}{}?limit={} - Топ товары по категории",
				ApiPaths.QUERY_BASE, ApiPaths.TOP_PRODUCTS, ApiPaths.CATEGORY_NAME, limit);
		List<ProductResponse> products = productQueryService.getTopProductsByCategory(category, limit);
		return ResponseEntity.ok(products);
	}

	@GetMapping(ApiPaths.PRODUCTS_BY_CATEGORY + ApiPaths.CATEGORY_NAME)
	public ResponseEntity<Page<ProductResponse>> getProductsByCategory(
			@PathVariable ProductCategory category,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "12") int size) {
		log.info("GET {}{}{} - Фильтрация по категории",
				ApiPaths.QUERY_BASE, ApiPaths.PRODUCTS_BY_CATEGORY, ApiPaths.CATEGORY_NAME);
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductResponse> products = productQueryService.filterProductsByCategory(category, pageable);
		return ResponseEntity.ok(products);
	}
}
