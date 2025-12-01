package com.example.TechSpot.modules.products.adapter;

import com.example.TechSpot.modules.api.product.ProductRepositoryProvider;
import com.example.TechSpot.modules.products.entity.Product;
import com.example.TechSpot.modules.products.service.query.ProductEntityQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductRepositoryProviderImpl implements ProductRepositoryProvider {

	private final ProductEntityQueryService productEntityQueryService;

	@Override
	public Product findById(Long productId) {
		return productEntityQueryService.findById(productId);
	}
}
