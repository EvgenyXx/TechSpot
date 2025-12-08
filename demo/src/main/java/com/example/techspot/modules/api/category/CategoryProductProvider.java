package com.example.techspot.modules.api.category;

import com.example.techspot.modules.categories.domain.entity.Category;

public interface CategoryProductProvider {

	Category findLeafCategoryById(Long categoryId);
}
