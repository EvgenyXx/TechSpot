package com.example.TechSpot.modules.categories.service.query;

import com.example.TechSpot.modules.categories.mapper.CategoryMapper;
import com.example.TechSpot.modules.categories.repository.CategoryRepository;
import com.example.TechSpot.modules.categories.dto.CategoryResponse;
import com.example.TechSpot.modules.categories.entity.Category;
import com.example.TechSpot.modules.categories.exception.CategoryNotFoundException;
import com.example.TechSpot.core.config.CacheNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryQueryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	@Transactional(readOnly = true)
	@Cacheable(value = CacheNames.CATEGORY_LIST,key = "'root'")
	public List<CategoryResponse> getRootCategories() {
		log.info("Запрос корневых категорий");

		List<Category> categories = categoryRepository.findByParentIsNull();
		List<CategoryResponse> categoryResponses = new ArrayList<>();

		for (Category category : categories) {
			boolean hasChildren = categoryRepository.existsByParent(category);

			CategoryResponse response = new CategoryResponse(
					category.getId(),
					category.getName(),
					category.getSlug(),
					hasChildren
			);
			categoryResponses.add(response);
		}

		log.info("Найдено {} корневых категорий", categoryResponses.size());
		return categoryResponses;
	}

	@Transactional(readOnly = true)
	@Cacheable(value = CacheNames.CATEGORY_LIST,key = "#parentId")
	public List<CategoryResponse> getSubcategories(Long parentId) {
		log.info("Запрос подкатегорий для родителя ID: {}", parentId);

		Category parentCategory = categoryRepository.findById(parentId)
				.orElseThrow(CategoryNotFoundException::new);

		List<Category> subcategories = categoryRepository.findByParent(parentCategory);
		List<CategoryResponse> categoryResponses = new ArrayList<>();

		for (Category category : subcategories) {
			boolean hasChildren = categoryRepository.existsByParent(category);
			CategoryResponse categoryResponse = new CategoryResponse(
					category.getId(),
					category.getName(),
					category.getSlug(),
					hasChildren
			);
			categoryResponses.add(categoryResponse);
		}

		log.info("Найдено {} подкатегорий для родителя ID: {}", categoryResponses.size(), parentId);
		return categoryResponses;
	}

	@Transactional(readOnly = true)
	@Cacheable(value = CacheNames.CATEGORIES,key = "#categoryId")
	public CategoryResponse getCategoryById(Long categoryId) {
		log.info("Запрос категории по ID: {}", categoryId);

		CategoryResponse response = categoryRepository.findById(categoryId)
				.map(categoryMapper::toCategoryResponse)
				.orElseThrow(CategoryNotFoundException::new);

		log.info("Категория найдена по ID {}: {}", categoryId, response.name());
		return response;
	}

	@Transactional(readOnly = true)
	@Cacheable(value = CacheNames.CATEGORIES,key = "#slug")
	public CategoryResponse getCategoryBySlug(String slug) {
		log.info("Запрос категории по slug: {}", slug);

		CategoryResponse response = categoryRepository.findBySlug(slug)
				.map(categoryMapper::toCategoryResponse)
				.orElseThrow(CategoryNotFoundException::new);

		log.info("Категория найдена по slug '{}': {}", slug, response.name());
		return response;
	}

	@Transactional(readOnly = true)
	public List<CategoryResponse> searchCategories(String query) {
		log.info("Поиск категорий по запросу: '{}'", query);

		List<CategoryResponse> result = categoryRepository.findByNameContainingIgnoreCase(query)
				.stream()
				.map(categoryMapper::toCategoryResponse)
				.toList();

		log.info("Найдено {} категорий по запросу '{}'", result.size(), query);
		return result;
	}
}