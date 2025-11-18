package com.example.TechSpot.service.category;


import com.example.TechSpot.dto.CategoryCreateRequest;
import com.example.TechSpot.dto.CategoryResponse;
import com.example.TechSpot.entity.Category;
import com.example.TechSpot.mapping.CategoryMapper;
import com.example.TechSpot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryCommandService {
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;


	public CategoryResponse createRootCategory(CategoryCreateRequest request) {

		Category root = categoryMapper.toCategoryCreateRoot(request);
		Category saveRoot = categoryRepository.save(root);

		return categoryMapper.toCategoryResponse(saveRoot);

	}

	// TODO: Реализовать этот метод
	public CategoryResponse createSubcategory(Long parentId, CategoryCreateRequest request) {
		// ХОД ДЕЙСТВИЙ:
		Category parent = categoryRepository.findById(parentId).orElseThrow();
		Category subcategory = Category.builder()
				.name(request.name())
				.slug(request.slug())
				.parent(parent)
				.build();

		Category saveSubcategory = categoryRepository.save(subcategory);

		return categoryMapper.toCategoryResponse(saveSubcategory);
		// 1. Найти родительскую категорию по parentId
		// 2. Если не найдена - выбросить CategoryNotFoundException
		// 3. Создать категорию с parent = найденная категория
		// 4. Сохранить в БД
		// 5. Преобразовать в CategoryResponse
		// 6. Вернуть результат
	}
}
