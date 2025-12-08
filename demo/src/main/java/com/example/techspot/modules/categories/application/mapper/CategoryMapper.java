package com.example.techspot.modules.categories.application.mapper;


import com.example.techspot.modules.categories.application.dto.CategoryCreateRequest;
import com.example.techspot.modules.categories.application.dto.CategoryResponse;
import com.example.techspot.modules.categories.domain.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {


	CategoryResponse toCategoryResponse(Category category);

	Category toCategoryCreateRoot(CategoryCreateRequest request);
}
