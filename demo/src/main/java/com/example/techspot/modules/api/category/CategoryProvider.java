package com.example.techspot.modules.api.category;

import com.example.techspot.modules.categories.domain.entity.Category;

public interface CategoryProvider {

	Category findById(Long categoryId);
}
