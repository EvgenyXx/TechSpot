package com.example.TechSpot.controller.product;

import com.example.TechSpot.constants.ApiPaths;
import com.example.TechSpot.dto.product.ProductResponse;
import com.example.TechSpot.entity.ProductCategory;
import com.example.TechSpot.security.CustomUserDetail;
import com.example.TechSpot.service.product.ProductQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import static com.example.TechSpot.constants.SecurityRoles.IS_SELLER_OR_ADMIN;


import java.util.List;

@RestController
@RequestMapping(ApiPaths.QUERY_BASE)
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Product Query", description = "API для поиска и фильтрации товаров")
public class ProductQueryController {

	private final ProductQueryService productQueryService;

	@Operation(
			summary = "Получить товар по ID",
			description = "Возвращает полную информацию о товаре по его идентификатору"
	)
	@ApiResponse(responseCode = "200", description = "Товар успешно найден")
	@ApiResponse(responseCode = "404", description = "Товар с указанным ID не найден")
	@GetMapping(ApiPaths.PRODUCT_ID)
	@PreAuthorize(IS_SELLER_OR_ADMIN)
	public ResponseEntity<ProductResponse> getProduct(
			@Parameter(description = "ID товара", example = "123")
			@PathVariable Long productId) {
		log.info("GET {}{} - Получение товара", ApiPaths.QUERY_BASE, ApiPaths.PRODUCT_ID);
		ProductResponse response = productQueryService.getProduct(productId);
		return ResponseEntity.ok(response);
	}

	@Operation(
			summary = "Получить все товары",
			description = "Возвращает paginated список всех товаров в каталоге"
	)
	@ApiResponse(responseCode = "200", description = "Список товаров успешно получен")
	@GetMapping
	public ResponseEntity<Page<ProductResponse>> getAllProducts(
			@Parameter(description = "Номер страницы", example = "0")
			@RequestParam(defaultValue = "0") int page,

			@Parameter(description = "Размер страницы", example = "12")
			@RequestParam(defaultValue = "12") int size) {

		log.info("GET {}?page={}&size={} - Все товары", ApiPaths.QUERY_BASE, page, size);
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductResponse> products = productQueryService.getAllProducts(pageable);
		return ResponseEntity.ok(products);
	}

	@Operation(
			summary = "Поиск товаров",
			description = "Ищет товары по названию или описанию с поддержкой пагинации"
	)
	@ApiResponse(responseCode = "200", description = "Результаты поиска успешно получены")
	@GetMapping(ApiPaths.SEARCH)
	public ResponseEntity<Page<ProductResponse>> searchProducts(
			@Parameter(description = "Поисковый запрос", example = "iPhone")
			@RequestParam String query,

			@Parameter(description = "Номер страницы", example = "0")
			@RequestParam(defaultValue = "0") int page,

			@Parameter(description = "Размер страницы", example = "12")
			@RequestParam(defaultValue = "12") int size) {

		log.info("GET {}{}?query={} - Поиск товаров", ApiPaths.QUERY_BASE, ApiPaths.SEARCH, query);
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductResponse> products = productQueryService.searchProducts(query, pageable);
		return ResponseEntity.ok(products);
	}

	@Operation(
			summary = "Получить мои товары",
			description = "Возвращает список товаров, созданных текущим пользователем"
	)
	@ApiResponse(responseCode = "200", description = "Список товаров пользователя успешно получен")
	@ApiResponse(responseCode = "401", description = "Требуется авторизация")
	@GetMapping(ApiPaths.GET_MY_PRODUCTS)
	public ResponseEntity<List<ProductResponse>> getMyProducts(
			@AuthenticationPrincipal CustomUserDetail customUserDetail) {

		log.info("GET {}{} - Мои товары", ApiPaths.QUERY_BASE, ApiPaths.GET_MY_PRODUCTS);
		List<ProductResponse> products = productQueryService.getMyProducts(customUserDetail.id());
		return ResponseEntity.ok(products);
	}

	@Operation(
			summary = "Получить топ товаров по категории",
			description = "Возвращает указанное количество самых популярных товаров в категории"
	)

	@ApiResponse(responseCode = "200", description = "Топ товаров успешно получен")
	@ApiResponse(responseCode = "400", description = "Невалидная категория")
	@GetMapping(ApiPaths.TOP_PRODUCTS + ApiPaths.CATEGORY_NAME)
	public ResponseEntity<List<ProductResponse>> getTopProductsByCategory(
			@Parameter(description = "Категория товара", example = "ELECTRONICS")
			@PathVariable ProductCategory category,

			@Parameter(description = "Количество товаров", example = "3")
			@RequestParam(defaultValue = "3") int limit) {

		log.info("GET {}{}{}?limit={} - Топ товары по категории",
				ApiPaths.QUERY_BASE, ApiPaths.TOP_PRODUCTS, ApiPaths.CATEGORY_NAME, limit);
		List<ProductResponse> products = productQueryService.getTopProductsByCategory(category, limit);
		return ResponseEntity.ok(products);
	}

	@Operation(
			summary = "Получить товары по категории",
			description = "Возвращает paginated список товаров указанной категории"
	)
	@ApiResponse(responseCode = "200", description = "Товары категории успешно получены")
	@ApiResponse(responseCode = "400", description = "Невалидная категория")
	@GetMapping(ApiPaths.PRODUCTS_BY_CATEGORY + ApiPaths.SLUG)
	public ResponseEntity<Page<ProductResponse>> getProductsByCategory(
			@Parameter(description = "Категория товара", example = "ELECTRONICS")
			@PathVariable String slug,

			@Parameter(description = "Номер страницы", example = "0")
			@RequestParam(defaultValue = "0") int page,

			@Parameter(description = "Размер страницы", example = "12")
			@RequestParam(defaultValue = "12") int size) {

		log.info("GET {}{}{} - Фильтрация по категории",
				ApiPaths.QUERY_BASE, ApiPaths.PRODUCTS_BY_CATEGORY, ApiPaths.CATEGORY_NAME);
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductResponse> products = productQueryService.filterProductsByCategory(slug, pageable);
		return ResponseEntity.ok(products);
	}
}