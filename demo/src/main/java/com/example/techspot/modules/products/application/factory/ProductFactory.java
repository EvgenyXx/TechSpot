package com.example.techspot.modules.products.application.factory;

import com.example.techspot.modules.products.application.dto.response.ProductCacheModel;
import com.example.techspot.modules.products.domain.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductFactory {

	public Product fromCache(ProductCacheModel c) {
		Product p = new Product();
		p.setId(c.id());
		p.setPrice(c.price());
		p.setQuantity(c.quantity());
		return p;
	}
}

