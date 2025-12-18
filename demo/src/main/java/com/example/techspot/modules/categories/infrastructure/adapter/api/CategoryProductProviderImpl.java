package com.example.techspot.modules.categories.infrastructure.adapter.api;

import com.example.techspot.modules.api.category.CategoryProductProvider;
import com.example.techspot.modules.categories.domain.entity.Category;
import com.example.techspot.modules.categories.application.exception.NotLeafCategoryException;
import com.example.techspot.modules.categories.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryProductProviderImpl implements CategoryProductProvider {



	private final CategoryRepository categoryRepository;

	@Override
	public Category findLeafCategoryById(Long categoryId) {
		log.info("Поиск конечной категории по ID: {}", categoryId);

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Категория с ID " + categoryId + " не найдена"));

		log.info("Найдена категория: {} (ID: {})", category.getName(), category.getId());

		boolean isLeaf = categoryRepository.isLeafCategory(categoryId);
		log.info("Результат isLeafCategory({}): {}", categoryId, isLeaf);

		if (!isLeaf) {
			log.warn("Категория {} не является конечной. ID: {}", category.getName(), categoryId);
			throw new NotLeafCategoryException();
		}

		log.info("Категория {} является конечной. ID: {}", category.getName(), categoryId);
		return category;
	}
}
