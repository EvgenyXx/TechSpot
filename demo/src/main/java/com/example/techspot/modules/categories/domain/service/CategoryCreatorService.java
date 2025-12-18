package com.example.techspot.modules.categories.domain.service;

import com.example.techspot.modules.categories.application.dto.CategoryCreateRequest;
import com.example.techspot.modules.categories.domain.entity.Category;
import com.example.techspot.modules.categories.infrastructure.repository.CategoryRepository;
import com.example.techspot.modules.categories.domain.factory.CategoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryCreatorService {

	private final CategoryRepository categoryRepository;
	private final CategoryFactory categoryFactory;
	private final CategoryValidationService validationService;

	public Category createRoot(CategoryCreateRequest req) {
		validationService.validateUniqueNameForParent(req.name(), null);
		validationService.validateUniqueSlugForParent(req.slug(), null);

		Category category = categoryFactory.buildRoot(req);
		return categoryRepository.save(category);
	}

	public Category createSub(Long parentId, CategoryCreateRequest req) {
		Category parent = categoryRepository.findById(parentId)
				.orElseThrow();

		validationService.validateUniqueNameForParent(req.name(), parent);
		validationService.validateUniqueSlugForParent(req.slug(), parent);

		Category sub = categoryFactory.buildSub(req, parent);
		return categoryRepository.save(sub);
	}
}
