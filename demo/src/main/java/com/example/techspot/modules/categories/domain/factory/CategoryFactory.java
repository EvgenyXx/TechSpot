package com.example.techspot.modules.categories.domain.factory;

import com.example.techspot.modules.categories.application.dto.CategoryCreateRequest;
import com.example.techspot.modules.categories.domain.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryFactory {

	public Category buildRoot(CategoryCreateRequest req) {
		return Category.builder()
				.name(req.name())
				.slug(req.slug())
				.parent(null)
				.build();
	}

	public Category buildSub(CategoryCreateRequest req, Category parent) {
		return Category.builder()
				.name(req.name())
				.slug(req.slug())
				.parent(parent)
				.build();
	}
}
