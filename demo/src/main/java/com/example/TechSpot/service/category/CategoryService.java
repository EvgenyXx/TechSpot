package com.example.TechSpot.service.category;


import com.example.TechSpot.dto.CategoryResponse;
import com.example.TechSpot.entity.Category;
import com.example.TechSpot.entity.ProductCategory;
import com.example.TechSpot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryService {

	private final CategoryRepository categoryRepository;



	public List<ProductCategory> getAllCategories() {
		return Arrays.asList(ProductCategory.values());
	}


	@Transactional(readOnly = true)
	public List<CategoryResponse> getRootCategories() {

		List<Category>categories = categoryRepository.findByParentIsNull();
		List<CategoryResponse>categoryResponses = new ArrayList<>();


		for (Category category : categories){
			boolean hasChildren = categoryRepository.existsByParent(category);

			CategoryResponse response = new CategoryResponse(
					category.getId(),
					category.getName(),
					category.getSlug(),
					hasChildren
			);
			categoryResponses.add(response);
		}
		return categoryResponses;

	}

//	// TODO: Реализовать этот метод
//	public List<CategoryResponse> getSubcategories(Long parentId) {
//		// ХОД ДЕЙСТВИЙ:
//		Category parentCategory = categoryRepository.findById(parentId)
//				.orElseThrow(CategoryNotFoundException::new);
//
//		List<Category>subcatigories = categoryRepository.findByParent(parentCategory);
//
//		List<CategoryResponse>categoryResponses = new ArrayList<>();
//
//		for (Category category : subcatigories){
//			boolean hasChildren = categoryRepository.existsByParent(category);
//			CategoryResponse categoryResponse = new CategoryResponse(
//					category.getId(),
//					category.getName(),
//					category.getSlug(),
//					hasChildren
//			);
//
//			categoryResponses.add(categoryResponse);
//		}
//		return categoryResponses;
//
//	}
}
