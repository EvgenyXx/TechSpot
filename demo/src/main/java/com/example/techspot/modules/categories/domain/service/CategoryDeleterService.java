package com.example.techspot.modules.categories.domain.service;

import com.example.techspot.modules.categories.domain.entity.Category;
import com.example.techspot.modules.categories.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryDeleterService {

	private final CategoryRepository categoryRepository;
	private final CategoryValidationService validationService;

	public void deleteRoot(Category category) {
		validationService.validateIsRoot(category);
		validationService.validateHasNoChildren(category.getId());
		validationService.validateHasNoProducts(category.getId());
		categoryRepository.deleteById(category.getId());
	}

	public void deleteSub(Category category) {
		validationService.validateIsSubcategory(category);
		validationService.validateHasNoChildren(category.getId());
		validationService.validateHasNoProducts(category.getId());
		categoryRepository.deleteById(category.getId());
	}
}
