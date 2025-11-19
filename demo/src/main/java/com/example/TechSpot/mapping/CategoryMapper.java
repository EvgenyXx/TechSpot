package com.example.TechSpot.mapping;


import com.example.TechSpot.dto.category.CategoryCreateRequest;
import com.example.TechSpot.dto.category.CategoryResponse;
import com.example.TechSpot.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {


	CategoryResponse toCategoryResponse(Category category);

	Category toCategoryCreateRoot(CategoryCreateRequest request);
}
