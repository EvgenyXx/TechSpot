package com.example.techspot.modules.api.product;

import com.example.techspot.modules.products.domain.entity.Product;

public interface ProductProvider {

	Product findById(Long productId);

	boolean existsByCategoryId(Long categoryId);
}
