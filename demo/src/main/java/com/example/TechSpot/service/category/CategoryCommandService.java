package com.example.TechSpot.service.category;

import com.example.TechSpot.dto.category.CategoryCreateRequest;
import com.example.TechSpot.dto.category.CategoryResponse;
import com.example.TechSpot.entity.Category;
import com.example.TechSpot.exception.category.CategoryAlreadyExistsException;
import com.example.TechSpot.exception.review.SlugAlreadyExistsException;
import com.example.TechSpot.mapping.CategoryMapper;
import com.example.TechSpot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CategoryCommandService {
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	private final CategoryValidationService categoryValidationService;

	public CategoryResponse createRootCategory(CategoryCreateRequest request) {
		log.info("Создание корневой категории: name='{}', slug='{}'",
				request.name(), request.slug());

		categoryValidationService.validateUniqueNameForParent(request.name(), null);
		categoryValidationService.validateUniqueSlugForParent(request.slug(), null);

		Category root = categoryMapper.toCategoryCreateRoot(request);
		Category saveRoot = categoryRepository.save(root);

		log.info("Корневая категория создана: ID={}, name='{}'",
				saveRoot.getId(), saveRoot.getName());

		return categoryMapper.toCategoryResponse(saveRoot);
	}

	public CategoryResponse createSubcategory(Long parentId, CategoryCreateRequest request) {
		log.info("Создание подкатегории: parentId={}, name='{}', slug='{}'",
				parentId, request.name(), request.slug());

		Category parent = categoryRepository.findById(parentId).orElseThrow();

		categoryValidationService.validateUniqueNameForParent(request.name(), parent);
		categoryValidationService.validateUniqueSlugForParent(request.slug(), parent);

		Category subcategory = Category.builder()
				.name(request.name())
				.slug(request.slug())
				.parent(parent)
				.build();

		Category saveSubcategory = categoryRepository.save(subcategory);

		log.info("Подкатегория создана: ID={}, name='{}', parentId={}",
				saveSubcategory.getId(), saveSubcategory.getName(), parentId);

		return categoryMapper.toCategoryResponse(saveSubcategory);
	}


}