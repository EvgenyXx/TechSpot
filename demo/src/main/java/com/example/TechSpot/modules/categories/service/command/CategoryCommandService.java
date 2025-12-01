package com.example.TechSpot.modules.categories.service.command;

import com.example.TechSpot.modules.categories.dto.CategoryCreateRequest;
import com.example.TechSpot.modules.categories.mapper.CategoryMapper;
import com.example.TechSpot.modules.categories.repository.CategoryRepository;
import com.example.TechSpot.modules.categories.dto.CategoryResponse;
import com.example.TechSpot.modules.categories.entity.Category;
import com.example.TechSpot.core.config.CacheNames;
import com.example.TechSpot.modules.categories.service.util.CategoryValidationService;
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
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	private final CategoryValidationService categoryValidationService;

	@Caching(
			put = {
					@CachePut(value = CacheNames.CATEGORIES, key = "#result.id"),
					@CachePut(value = CacheNames.CATEGORIES, key = "#result.slug")
			},
			evict = {
					@CacheEvict(value = CacheNames.CATEGORY_LIST, allEntries = true)
			}
	)
	public CategoryResponse createRootCategory(CategoryCreateRequest request) {
		log.info("Создание корневой категории: name='{}', slug='{}'",
				request.name(), request.slug());

		categoryValidationService.validateUniqueNameForParent(request.name(), null);
		categoryValidationService.validateUniqueSlugForParent(request.slug(), null);

		Category root = categoryMapper.toCategoryCreateRoot(request);
		Category saveRoot = categoryRepository.save(root);

		log.info("Корневая категория создана: ID={}, name='{}'",
				saveRoot.getId(), saveRoot.getName());

		return categoryMapper.toCategoryResponse(saveRoot);
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
	public CategoryResponse createSubcategory(Long parentId, CategoryCreateRequest request) {
		log.info("Создание подкатегории: parentId={}, name='{}', slug='{}'",
				parentId, request.name(), request.slug());

		Category parent = categoryRepository.findById(parentId).orElseThrow();

		categoryValidationService.validateUniqueNameForParent(request.name(), parent);
		categoryValidationService.validateUniqueSlugForParent(request.slug(), parent);

		Category subcategory = Category.builder()
				.name(request.name())
				.slug(request.slug())
				.parent(parent)
				.build();

		Category saveSubcategory = categoryRepository.save(subcategory);

		log.info("Подкатегория создана: ID={}, name='{}', parentId={}",
				saveSubcategory.getId(), saveSubcategory.getName(), parentId);

		return categoryMapper.toCategoryResponse(saveSubcategory);
	}


}