package com.example.TechSpot.modules.api.category;

import com.example.TechSpot.modules.categories.entity.Category;

public interface CategoryProductProvider {

	Category findLeafCategoryById(Long categoryId);
}
