package com.example.TechSpot.modules.api.product;

import com.example.TechSpot.modules.products.entity.Product;

public interface ProductRepositoryProvider {

	Product findById(Long productId);
}
