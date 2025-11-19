package com.example.TechSpot.service.category;

import com.example.TechSpot.entity.Category;

import com.example.TechSpot.exception.category.NotLeafCategoryException;
import com.example.TechSpot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryFinder {

	private final CategoryRepository categoryRepository;

	public Category findLeafCategoryById(Long categoryId) {
		log.info("Поиск конечной категории по ID: {}", categoryId);

		// 1. Проверяем что категория существует
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Категория с ID " + categoryId + " не найдена"));

		log.info("Найдена категория: {} (ID: {})", category.getName(), category.getId());

		// 2. ВРЕМЕННО логируем результат проверки
		boolean isLeaf = categoryRepository.isLeafCategory(categoryId);
		log.info("Результат isLeafCategory({}): {}", categoryId, isLeaf);

//		 3. ВРЕМЕННО закомментируйте исключение
		 if (!isLeaf) {
		     log.warn("Категория {} не является конечной. ID: {}", category.getName(), categoryId);
		     throw new NotLeafCategoryException();
		 }

		log.info("Категория {} является конечной. ID: {}", category.getName(), categoryId);
		return category;
	}
}