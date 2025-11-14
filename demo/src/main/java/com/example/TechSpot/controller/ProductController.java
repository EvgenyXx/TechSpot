package com.example.TechSpot.controller;


import com.example.TechSpot.service.product.ProductFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

	private final ProductFinder productFinder;





//	@GetMapping("/{id}")
//	public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
//		log.info("GET /api/v1/products/{} - Получение товара", id);
//		ProductResponse response = productService.getProduct(id);
//		return ResponseEntity.ok(response);
//	}




//
//	@GetMapping
//	public ResponseEntity<Page<ProductResponse>> getAllProducts(
//			@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "12") int size) {
//
//		log.info("GET /api/v1/products?page={}&size={} - Все товары", page, size);
//		Pageable pageable = PageRequest.of(page, size);
//		Page<ProductResponse> products = productService.getAllProducts(pageable);
//		return ResponseEntity.ok(products);
//	}

	// ==================== ПОИСК И ФИЛЬТРАЦИЯ ====================

//	@GetMapping("/search")
//	public ResponseEntity<Page<ProductResponse>> searchProducts(
//			@RequestParam String query,
//			@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "12") int size) {
//
//		log.info("GET /api/v1/products/search?query={} - Поиск товаров", query);
//		Pageable pageable = PageRequest.of(page, size);
//		Page<ProductResponse> products = productService.searchProducts(query, pageable);
//		return ResponseEntity.ok(products);
//	}

//	@GetMapping("/category/{category}")
//	public ResponseEntity<Page<ProductResponse>> getProductsByCategory(
//			@PathVariable ProductCategory category,
//			@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "12") int size) {
//
//		log.info("GET /api/v1/products/category/{} - Фильтрация по категории", category);
//		Pageable pageable = PageRequest.of(page, size);
//		Page<ProductResponse> products = productService.filterProductsByCategory(category, pageable);
//		return ResponseEntity.ok(products);
//	}

	// ==================== КАТЕГОРИИ ====================
//
//	@GetMapping("/categories")
//	public ResponseEntity<List<ProductCategory>> getAllCategories() {
//		log.info("GET /api/v1/products/categories - Все категории");
//		List<ProductCategory> categories = productService.getAllCategories();
//		return ResponseEntity.ok(categories);
//	}

	// ==================== ДЛЯ ПРОДАВЦОВ ====================

//	@GetMapping("/my-products")
//	public ResponseEntity<List<ProductResponse>> getMyProducts(
//			@RequestHeader("X-Customer-Id") UUID customerId) {
//
//		log.info("GET /api/v1/products/my-products - Мои товары");
//		List<ProductResponse> products = productService.getMyProducts(customerId);
//		return ResponseEntity.ok(products);
//	}

	// ==================== ТОП ТОВАРЫ ====================

//	@GetMapping("/top/{category}")
//	public ResponseEntity<List<ProductResponse>> getTopProductsByCategory(
//			@PathVariable ProductCategory category,
//			@RequestParam(defaultValue = "3") int limit) {
//
//		log.info("GET /api/v1/products/top/{}?limit={} - Топ товары по категории", category, limit);
//		List<ProductResponse> products = productService.getTopProductsByCategory(category, limit);
//		return ResponseEntity.ok(products);
//	}


}
