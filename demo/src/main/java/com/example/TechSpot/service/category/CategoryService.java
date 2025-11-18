package com.example.TechSpot.service.category;


import com.example.TechSpot.entity.ProductCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryService {



	public List<ProductCategory> getAllCategories() {
		return Arrays.asList(ProductCategory.values());
	}
}
