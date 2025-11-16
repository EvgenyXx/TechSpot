package com.example.TechSpot.controller.category;

import com.example.TechSpot.constants.ApiPaths;
import com.example.TechSpot.entity.ProductCategory;
import com.example.TechSpot.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.CATEGORIES_BASE)
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Categories", description = "API для управления категориями товаров")
public class CategoryController {

	private final CategoryService categoryService;

	@Operation(
			summary = "Получить все категории",
			description = "Возвращает список всех доступных категорий товаров"
	)
	@ApiResponse(responseCode = "200", description = "Список категорий успешно получен")
	@GetMapping
	public ResponseEntity<List<ProductCategory>> getAllCategories() {
		log.info("GET {} - Получение всех категорий", ApiPaths.CATEGORIES_BASE);
		List<ProductCategory> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}
}