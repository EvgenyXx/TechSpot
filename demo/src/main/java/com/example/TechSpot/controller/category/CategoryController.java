package com.example.TechSpot.controller.category;


import com.example.TechSpot.constants.ApiPaths;
import com.example.TechSpot.entity.ProductCategory;
import com.example.TechSpot.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.CATEGORIES_BASE)
@Log4j2
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<ProductCategory>> getAllCategories() {
		log.info("GET /api/v1/products/categories - Все категории");
		List<ProductCategory> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}


}
