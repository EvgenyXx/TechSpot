package com.example.techspot.modules.categories.application.query;

import com.example.techspot.modules.categories.application.factory.CategoryResponseFactory;
import com.example.techspot.modules.categories.infrastructure.repository.CategoryRepository;
import com.example.techspot.modules.categories.application.dto.CategoryResponse;
import com.example.techspot.modules.categories.domain.entity.Category;
import com.example.techspot.modules.categories.application.exception.CategoryNotFoundException;
import com.example.techspot.core.config.CacheNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryQueryService {

	private final CategoryRepository categoryRepository;
	private final CategoryResponseFactory responseFactory;


	@Transactional(readOnly = true)
	@Cacheable(value = CacheNames.CATEGORY_LIST, key = "'root'")
	public List<CategoryResponse> getRootCategories() {
		log.info("Запрос корневых категорий");

		List<Category> categories = categoryRepository.findByParentIsNull();

		return categories.stream()
				.map(category -> responseFactory.buildWithChildrenFlag(
						category,
						categoryRepository.existsByParent(category)
				))
				.toList();
	}


	@Transactional(readOnly = true)
	@Cacheable(value = CacheNames.CATEGORY_LIST, key = "#parentId")
	public List<CategoryResponse> getSubcategories(Long parentId) {
		log.info("Запрос подкатегорий для родителя ID: {}", parentId);

		Category parent = categoryRepository.findById(parentId)
				.orElseThrow(CategoryNotFoundException::new);

		return categoryRepository.findByParent(parent).stream()
				.map(category -> responseFactory.buildWithChildrenFlag(
						category,
						categoryRepository.existsByParent(category)
				))
				.toList();
	}


	@Transactional(readOnly = true)
	@Cacheable(value = CacheNames.CATEGORIES, key = "#categoryId")
	public CategoryResponse getCategoryById(Long categoryId) {
		log.info("Запрос категории по ID: {}", categoryId);

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(CategoryNotFoundException::new);

		return responseFactory.build(category);
	}


	@Transactional(readOnly = true)
	@Cacheable(value = CacheNames.CATEGORIES, key = "#slug")
	public CategoryResponse getCategoryBySlug(String slug) {
		log.info("Запрос категории по slug: {}", slug);

		Category category = categoryRepository.findBySlug(slug)
				.orElseThrow(CategoryNotFoundException::new);

		return responseFactory.build(category);
	}


	@Transactional(readOnly = true)
	public List<CategoryResponse> searchCategories(String query) {
		log.info("Поиск категорий: '{}'", query);

		return categoryRepository.findByNameContainingIgnoreCase(query).stream()
				.map(responseFactory::build)
				.toList();
	}
}
