package com.example.TechSpot.controller;

import com.example.TechSpot.dto.CategoryCreateRequest;
import com.example.TechSpot.dto.CategoryResponse;
import com.example.TechSpot.service.category.CategoryCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Category Management", description = "API для управления категориями")
public class CategoryCommandController {

	private final CategoryCommandService categoryCommandService;

//	@Operation(
//			summary = "Создать категорию",
//			description = """
//            Создает новую категорию. Может быть как корневой, так и подкатегорией.
//
//            **Правила:**
//            - Название: 2-50 символов
//            - Slug: только латинские буквы, цифры и дефисы
//            - parentId: опционально, если указан - создается подкатегория
//            """
//	)
//	@ApiResponse(responseCode = "201", description = "Категория успешно создана")
//	@ApiResponse(responseCode = "400", description = "Невалидные данные")
//	@ApiResponse(responseCode = "404", description = "Родительская категория не найдена")
//	@ApiResponse(responseCode = "409", description = "Категория с таким slug уже существует")
//	@PostMapping
//	public ResponseEntity<CategoryResponse> createCategory(
//			@RequestBody @Valid CategoryCreateRequest request
//	) {
//		log.info("POST /api/v1/categories - Создание категории: {}", request.name());
//
//		CategoryResponse response = categoryCommandService.createCategory(request);
//
//		log.info("Категория создана: ID {}", response.id());
//		return ResponseEntity.status(HttpStatus.CREATED).body(response);
//	}
}
