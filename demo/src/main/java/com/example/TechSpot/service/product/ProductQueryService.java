package com.example.TechSpot.service.product;


import com.example.TechSpot.dto.product.ProductResponse;
import com.example.TechSpot.entity.ProductCategory;
import com.example.TechSpot.exception.product.ProductNotFoundException;
import com.example.TechSpot.mapping.ProductMapper;
import com.example.TechSpot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductQueryService {
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;


	@Transactional(readOnly = true)
	public ProductResponse getProduct(Long id) {
		return productRepository
				.findById(id)
				.map(productMapper::toResponseProduct)
				.orElseThrow(ProductNotFoundException::new);
	}

	@Transactional(readOnly = true)
	public Page<ProductResponse> getAllProducts(Pageable pageable) {
		return productRepository
				.findAll(pageable)
				.map(productMapper::toResponseProduct);
	}


	@Transactional(readOnly = true)
	public Page<ProductResponse> searchProducts(String query, Pageable pageable) {
		return productRepository
				.searchProduct(
						query,
						query,
						pageable
				).map(productMapper::toResponseProduct);
	}

	@Transactional(readOnly = true)
	public Page<ProductResponse> filterProductsByCategory(ProductCategory category, Pageable pageable) {
		return productRepository
				.findByCategory(category,pageable)
				.map(productMapper::toResponseProduct);
	}

	@Transactional(readOnly = true)
	public List<ProductResponse> getMyProducts(UUID customerId) {
		return productRepository
				.findByUserId(customerId)
				.stream()
				.map(productMapper::toResponseProduct)
				.toList();

	}

	@Transactional(readOnly = true)
	public List<ProductResponse> getTopProductsByCategory(ProductCategory category, int limit) {
		Pageable pageable = PageRequest.of(0,limit);

		return productRepository.findTopByCategoryOrderByPriceDesc(category,pageable)
				.stream()
				.map(productMapper::toResponseProduct)
				.toList();
	}
}
