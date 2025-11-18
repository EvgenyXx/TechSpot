package com.example.TechSpot.mapping;


import com.example.TechSpot.dto.CategoryCreateRequest;
import com.example.TechSpot.dto.CategoryResponse;
import com.example.TechSpot.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {


	CategoryResponse toCategoryResponse(Category category);

	Category toCategoryCreateRoot(CategoryCreateRequest request);
}
