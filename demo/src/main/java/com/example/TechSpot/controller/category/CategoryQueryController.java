package com.example.TechSpot.controller.category;

import com.example.TechSpot.constants.ApiPaths;
import com.example.TechSpot.dto.CategoryResponse;
import com.example.TechSpot.service.category.CategoryQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.CATEGORIES_BASE)
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Category Queries", description = "API для получения категорий товаров")
public class CategoryQueryController {

	private final CategoryQueryService categoryQueryService;

	@Operation(
			summary = "Получить корневые категории",
			description = "Возвращает список всех корневых категорий (без родителя)"
	)
	@ApiResponse(responseCode = "200", description = "Список корневых категорий получен успешно")
	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getRootCategories() {
		log.info("GET {} - Получение корневых категорий", ApiPaths.CATEGORIES_BASE);

		List<CategoryResponse> categoryResponses = categoryQueryService.getRootCategories();

		log.info("GET {} - Найдено {} корневых категорий",
				ApiPaths.CATEGORIES_BASE, categoryResponses.size());
		return ResponseEntity.status(HttpStatus.OK).body(categoryResponses);
	}

	@Operation(
			summary = "Получить подкатегории",
			description = "Возвращает список подкатегорий для указанной родительской категории"
	)
	@ApiResponse(responseCode = "200", description = "Список подкатегорий получен успешно")
	@ApiResponse(responseCode = "404", description = "Родительская категория не найдена")
	@GetMapping(ApiPaths.PARENT_ID + ApiPaths.SUBCATEGORIES)
	public ResponseEntity<List<CategoryResponse>> getSubcategories(@PathVariable Long parentId) {
		log.info("GET {}{}{} - Получение подкатегорий для родителя ID: {}",
				ApiPaths.CATEGORIES_BASE, ApiPaths.PARENT_ID, ApiPaths.SUBCATEGORIES, parentId);

		List<CategoryResponse> categoryResponses = categoryQueryService.getSubcategories(parentId);

		log.info("GET {}{}{} - Найдено {} подкатегорий",
				ApiPaths.CATEGORIES_BASE, ApiPaths.PARENT_ID, ApiPaths.SUBCATEGORIES, categoryResponses.size());
		return ResponseEntity.status(HttpStatus.OK).body(categoryResponses);
	}

	@Operation(
			summary = "Получить категорию по ID",
			description = "Возвращает информацию о категории по её идентификатору"
	)
	@ApiResponse(responseCode = "200", description = "Категория найдена")
	@ApiResponse(responseCode = "404", description = "Категория не найдена")
	@GetMapping(ApiPaths.CATEGORY_ID)
	public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId) {
		log.info("GET {}{} - Получение категории по ID: {}",
				ApiPaths.CATEGORIES_BASE, ApiPaths.CATEGORY_ID, categoryId);

		CategoryResponse categoryResponse = categoryQueryService.getCategoryById(categoryId);

		log.info("GET {}{} - Категория найдена: ID {}, название: {}",
				ApiPaths.CATEGORIES_BASE, ApiPaths.CATEGORY_ID, categoryId, categoryResponse.name());
		return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
	}

	@Operation(
			summary = "Получить категорию по slug",
			description = "Возвращает информацию о категории по её человеко-читаемому идентификатору (slug)"
	)
	@ApiResponse(responseCode = "200", description = "Категория найдена")
	@ApiResponse(responseCode = "404", description = "Категория не найдена")
	@GetMapping(ApiPaths.BY_SLUG + ApiPaths.SLUG)
	public ResponseEntity<CategoryResponse> getCategoryBySlug(@PathVariable String slug) {
		log.info("GET {}{}{} - Получение категории по slug: {}",
				ApiPaths.CATEGORIES_BASE, ApiPaths.BY_SLUG, ApiPaths.SLUG, slug);

		CategoryResponse categoryResponse = categoryQueryService.getCategoryBySlug(slug);

		log.info("GET {}{}{} - Категория найдена: slug {}, название: {}",
				ApiPaths.CATEGORIES_BASE, ApiPaths.BY_SLUG, ApiPaths.SLUG, slug, categoryResponse.name());
		return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
	}

	@Operation(
			summary = "Поиск категорий",
			description = "Возвращает список категорий, название которых содержит указанную строку (без учёта регистра)"
	)
	@ApiResponse(responseCode = "200", description = "Поиск выполнен успешно")
	@GetMapping(ApiPaths.SEARCH)
	public ResponseEntity<List<CategoryResponse>> searchCategories(@RequestParam String query) {
		log.info("GET {}{} - Поиск категорий по запросу: '{}'",
				ApiPaths.CATEGORIES_BASE, ApiPaths.SEARCH, query);

		List<CategoryResponse> categories = categoryQueryService.searchCategories(query);

		log.info("GET {}{} - Найдено {} категорий по запросу '{}'",
				ApiPaths.CATEGORIES_BASE, ApiPaths.SEARCH, categories.size(), query);
		return ResponseEntity.ok(categories);
	}
}