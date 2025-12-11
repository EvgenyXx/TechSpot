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
@RequestMapping(ApiPaths.ADMIN_CATEGORY)
@PreAuthorize(IS_ADMIN)
@Tag(name = "Admin Categories", description = "API для управления категориями (только для администраторов)")
public class AdminCategoryCommandController {

	private final CategoryCommandService categoryCommandService;

	// ------------------------------ CREATE ROOT ------------------------------
	@Operation(summary = "Создать корневую категорию")
	@PostMapping(ApiPaths.ROOT)
	public ResponseEntity<CategoryResponse> createCategoryRoot(
			@RequestBody @Valid CategoryCreateRequest request
	) {
		log.info("Создание корневой категории: {}", request.name());
		CategoryResponse response = categoryCommandService.createRootCategory(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// ------------------------------ CREATE SUB ------------------------------
	@Operation(summary = "Создать подкатегорию")
	@PostMapping(ApiPaths.PARENT_ID + ApiPaths.SUBCATEGORIES)
	public ResponseEntity<CategoryResponse> createSubcategories(
			@PathVariable Long parentId,
			@Valid @RequestBody CategoryCreateRequest request
	) {
		log.info("Создание подкатегории для parentId={}", parentId);
		CategoryResponse response = categoryCommandService.createSubcategory(parentId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// ------------------------------ DELETE ROOT ------------------------------
	@Operation(summary = "Удалить корневую категорию")
	@DeleteMapping(ApiPaths.CATEGORY_ID + ApiPaths.ROOT)
	public ResponseEntity<Void> deleteRoot(@PathVariable Long categoryId) {
		log.info("DELETE root category id={}", categoryId);
		categoryCommandService.deleteRootCategoryById(categoryId);
		return ResponseEntity.noContent().build();
	}

	// ------------------------------ DELETE SUB ------------------------------
	@Operation(summary = "Удалить подкатегорию")
	@DeleteMapping(ApiPaths.CATEGORY_ID + ApiPaths.SUB)
	public ResponseEntity<Void> deleteSub(@PathVariable Long categoryId) {
		log.info("DELETE subcategory id={}", categoryId);
		categoryCommandService.deleteSubcategoryById(categoryId);
		return ResponseEntity.noContent().build();
	}
}

