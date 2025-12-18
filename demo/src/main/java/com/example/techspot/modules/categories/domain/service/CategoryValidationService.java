package com.example.techspot.modules.categories.domain.service;

import com.example.techspot.modules.categories.infrastructure.repository.CategoryRepository;
import com.example.techspot.modules.categories.domain.entity.Category;
import com.example.techspot.modules.categories.application.exception.CategoryAlreadyExistsException;
import com.example.techspot.modules.categories.application.exception.SlugAlreadyExistsException;
import com.example.techspot.modules.categories.application.exception.CategoryHasChildrenException;
import com.example.techspot.modules.categories.application.exception.CategoryHasProductsException;
import com.example.techspot.modules.categories.application.exception.CategoryIsNotRootException;
import com.example.techspot.modules.categories.application.exception.CategoryIsRootException;
import com.example.techspot.modules.api.product.ProductProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CategoryValidationService {

	private final CategoryRepository categoryRepository;
	private final ProductProvider productProvider;



	public void validateUniqueNameForParent(String name, Category parent) {
		log.debug("Проверка уникальности имени: name='{}', parent={}",
				name, parent != null ? parent.getId() : "null");

		if (categoryRepository.existsByNameAndParent(name, parent)) {
			log.warn("Нарушение уникальности имени: name='{}' уже существует для parent={}",
					name, parent != null ? parent.getId() : "root");
			throw new CategoryAlreadyExistsException();
		}

		log.debug("Проверка имени пройдена: name='{}' уникально", name);
	}

	public void validateUniqueSlugForParent(String slug, Category parent) {
		log.debug("Проверка уникальности slug: slug='{}', parent={}",
				slug, parent != null ? parent.getId() : "null");

		if (categoryRepository.existsBySlugAndParent(slug, parent)) {
			log.warn("Нарушение уникальности slug: slug='{}' уже существует для parent={}",
					slug, parent != null ? parent.getId() : "root");
			throw new SlugAlreadyExistsException();
		}

		log.debug("Проверка slug пройдена: slug='{}' уникален", slug);
	}


	public void validateIsRoot(Category category) {
		log.debug("Проверка, что категория — root: id={}", category.getId());

		if (category.getParent() != null) {
			log.warn("Категория id={} НЕ root, parentId={}", category.getId(), category.getParent().getId());
			throw new CategoryIsNotRootException();
		}

		log.debug("Категория id={} является root", category.getId());
	}

	public void validateIsSubcategory(Category category) {
		log.debug("Проверка, что категория — subcategory: id={}", category.getId());

		if (category.getParent() == null) {
			log.warn("Категория id={} является root", category.getId());
			throw new CategoryIsRootException();
		}

		log.debug("Категория id={} является subcategory", category.getId());
	}

	public void validateHasNoChildren(Long categoryId) {
		log.debug("Проверка отсутствия дочерних категорий: id={}", categoryId);

		if (categoryRepository.existsByParentId(categoryId)) {
			log.warn("Категория id={} имеет дочерние категории", categoryId);
			throw new CategoryHasChildrenException();
		}

		log.debug("Категория id={} не имеет дочерних категорий", categoryId);
	}

	public void validateHasNoProducts(Long categoryId) {
		log.debug("Проверка отсутствия товаров в категории: id={}", categoryId);

		if (productProvider.existsByCategoryId(categoryId)) {
			log.warn("Категория id={} содержит товары", categoryId);
			throw new CategoryHasProductsException();
		}

		log.debug("Категория id={} не содержит товаров", categoryId);
	}
}
