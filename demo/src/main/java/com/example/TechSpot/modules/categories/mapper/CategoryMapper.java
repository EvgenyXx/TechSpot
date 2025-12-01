package com.example.TechSpot.modules.categories.mapper;


import com.example.TechSpot.modules.categories.dto.CategoryCreateRequest;
import com.example.TechSpot.modules.categories.dto.CategoryResponse;
import com.example.TechSpot.modules.categories.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {


	CategoryResponse toCategoryResponse(Category category);

	Category toCategoryCreateRoot(CategoryCreateRequest request);
}
