package com.example.techspot.modules.products.infrastructure.adapter;

import com.example.techspot.modules.api.product.ProductProvider;
import com.example.techspot.modules.products.domain.entity.Product;
import com.example.techspot.modules.products.domain.service.query.ProductEntityQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductProviderImpl implements ProductProvider {

	private final ProductEntityQueryService productEntityQueryService;

	@Override
	public Product findById(Long productId) {
		return productEntityQueryService.findById(productId);
	}
}
