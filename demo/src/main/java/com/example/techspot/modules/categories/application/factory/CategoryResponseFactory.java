package com.example.techspot.modules.categories.application.factory;

import com.example.techspot.modules.categories.application.dto.CategoryResponse;
import com.example.techspot.modules.categories.domain.entity.Category;
import com.example.techspot.modules.categories.application.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryResponseFactory {

	private final CategoryMapper mapper;

	public CategoryResponse build(Category category) {
		return mapper.toCategoryResponse(category);
	}

	public CategoryResponse buildWithChildrenFlag(Category category, boolean hasChildren) {
		return new CategoryResponse(
				category.getId(),
				category.getName(),
				category.getSlug(),
				hasChildren
		);
	}
}
