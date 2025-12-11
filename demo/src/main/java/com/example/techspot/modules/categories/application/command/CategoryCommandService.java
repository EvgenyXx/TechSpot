package com.example.techspot.modules.categories.application.command;


import com.example.techspot.modules.categories.application.dto.CategoryCreateRequest;
import com.example.techspot.modules.categories.application.dto.CategoryResponse;
import com.example.techspot.modules.categories.application.exception.CategoryNotFoundException;
import com.example.techspot.modules.categories.application.factory.CategoryResponseFactory;
import com.example.techspot.modules.categories.domain.entity.Category;
import com.example.techspot.modules.categories.domain.service.CategoryCreatorService;
import com.example.techspot.modules.categories.domain.service.CategoryDeleterService;
import com.example.techspot.modules.categories.infrastructure.repository.CategoryRepository;
import com.example.techspot.core.config.CacheNames;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CategoryCommandService {

	private final CategoryCreatorService creatorService;
	private final CategoryDeleterService deleterService;
	private final CategoryResponseFactory responseFactory;
	private final CategoryRepository categoryRepository;


	@Caching(
			put = {
					@CachePut(value = CacheNames.CATEGORIES, key = "#result.id"),
					@CachePut(value = CacheNames.CATEGORIES, key = "#result.slug")
			},
			evict = {
					@CacheEvict(value = CacheNames.CATEGORY_LIST, allEntries = true)
			}
	)
	public CategoryResponse createRootCategory(CategoryCreateRequest req) {
		log.info("Создание корневой категории: name='{}', slug='{}'", req.name(), req.slug());
		Category root = creatorService.createRoot(req);
		return responseFactory.build(root);
	}


	@Caching(
			put = {
					@CachePut(value = CacheNames.CATEGORIES, key = "#result.id"),
					@CachePut(value = CacheNames.CATEGORIES, key = "#result.slug")
			},
			evict = {
					@CacheEvict(value = CacheNames.CATEGORY_LIST, allEntries = true)
			}
	)
	public CategoryResponse createSubcategory(Long parentId, CategoryCreateRequest req) {
		log.info("Создание подкатегории: parentId={}, name='{}', slug='{}'", parentId, req.name(), req.slug());
		Category sub = creatorService.createSub(parentId, req);
		return responseFactory.build(sub);
	}


	@Caching(
			evict = {
					@CacheEvict(value = CacheNames.CATEGORIES, key = "#categoryId"),
					@CacheEvict(value = CacheNames.CATEGORY_LIST, allEntries = true)
			}
	)
	public void deleteRootCategoryById(Long categoryId) {
		Category root = findById(categoryId);
		deleterService.deleteRoot(root);
	}


	@Caching(
			evict = {
					@CacheEvict(value = CacheNames.CATEGORIES, key = "#categoryId"),
					@CacheEvict(value = CacheNames.CATEGORY_LIST, allEntries = true)
			}
	)
	public void deleteSubcategoryById(Long categoryId) {
		Category sub = findById(categoryId);
		deleterService.deleteSub(sub);
	}


	private Category findById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(CategoryNotFoundException::new);
	}
}
