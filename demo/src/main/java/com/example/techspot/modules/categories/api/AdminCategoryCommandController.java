package com.example.techspot.modules.categories.api;

import com.example.techspot.common.constants.ApiPaths;
import com.example.techspot.modules.categories.application.dto.CategoryCreateRequest;
import com.example.techspot.modules.categories.application.dto.CategoryResponse;
import com.example.techspot.modules.categories.application.command.CategoryCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.techspot.common.constants.SecurityRoles.IS_ADMIN;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(ApiPaths.CATEGORIES_BASE)
@PreAuthorize(IS_ADMIN)
@Tag(name = "Admin Categories", description = "API для управления категориями (только для администраторов)")
public class AdminCategoryCommandController {

	private final CategoryCommandService categoryCommandService;

	@Operation(
			summary = "Создать корневую категорию",
			description = """
            Создает новую корневую категорию. Требуются права администратора.
            
            **Правила:**
            - Название: 2-50 символов
            - Slug: только латинские буквы, цифры и дефисы
            - Категория создается как корневая (без родителя)
            """
	)
	@ApiResponse(responseCode = "201", description = "Корневая категория успешно создана")
	@ApiResponse(responseCode = "400", description = "Невалидные данные запроса")
	@ApiResponse(responseCode = "401", description = "Требуется аутентификация")
	@ApiResponse(responseCode = "403", description = "Недостаточно прав")
	@ApiResponse(responseCode = "409", description = "Категория с таким slug уже существует")
	@PostMapping(ApiPaths.ROOT)
	public ResponseEntity<CategoryResponse> createCategoryRoot(
			@RequestBody @Valid CategoryCreateRequest request) {

		log.info("POST {}{} - Создание корневой категории: {}",
				ApiPaths.CATEGORIES_BASE, ApiPaths.ROOT, request.name());

		CategoryResponse response = categoryCommandService.createRootCategory(request);

		log.info("Корневая категория создана: ID {}, название: {}",
				response.id(), response.name());

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(response);
	}

	@Operation(
			summary = "Создать подкатегорию",
			description = """
            Создает новую подкатегорию для указанной родительской категории. 
            Требуются права администратора.
            
            **Правила:**
            - Родительская категория должна существовать
            - Название: 2-50 символов  
            - Slug: только латинские буквы, цифры и дефисы
            """
	)
	@ApiResponse(responseCode = "201", description = "Подкатегория успешно создана")
	@ApiResponse(responseCode = "400", description = "Невалидные данные запроса")
	@ApiResponse(responseCode = "401", description = "Требуется аутентификация")
	@ApiResponse(responseCode = "403", description = "Недостаточно прав")
	@ApiResponse(responseCode = "404", description = "Родительская категория не найдена")
	@ApiResponse(responseCode = "409", description = "Категория с таким slug уже существует")
	@PostMapping(ApiPaths.PARENT_ID + ApiPaths.SUBCATEGORIES)
	public ResponseEntity<CategoryResponse> createSubcategories(
			@PathVariable Long parentId,
			@Valid @RequestBody CategoryCreateRequest request) {

		log.info("POST {}{}{} - Создание подкатегории для родителя ID: {}, название: {}",
				ApiPaths.CATEGORIES_BASE, ApiPaths.PARENT_ID, ApiPaths.SUBCATEGORIES,
				parentId, request.name());

		CategoryResponse response = categoryCommandService.createSubcategory(parentId, request);

		log.info("Подкатегория создана: ID {}, название: {}, родитель: {}",
				response.id(), response.name(), parentId);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(response);
	}
}