package com.example.techspot.modules.categories.infrastructure.adapter.api;

import com.example.techspot.modules.api.category.CategoryProvider;
import com.example.techspot.modules.categories.application.exception.CategoryNotFoundException;
import com.example.techspot.modules.categories.domain.entity.Category;
import com.example.techspot.modules.categories.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
//todo log
public class CategoryProviderImpl implements CategoryProvider {
	private final CategoryRepository categoryRepository;

	@Override
	public Category findById(Long categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
	}
}
