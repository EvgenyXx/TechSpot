package com.example.techspot.modules.products.application.command;


import com.example.techspot.modules.api.category.CategoryProductProvider;
import com.example.techspot.modules.api.user.UserProvider;
import com.example.techspot.modules.categories.domain.entity.Category;
import com.example.techspot.modules.products.application.dto.request.ProductCreateRequest;
import com.example.techspot.modules.products.application.dto.response.ProductResponse;
import com.example.techspot.modules.products.application.factory.ProductResponseFactory;
import com.example.techspot.modules.products.domain.entity.Product;
import com.example.techspot.modules.products.application.mapper.ProductMapper;
import com.example.techspot.modules.products.infrastructure.repository.ProductRepository;
import com.example.techspot.modules.products.domain.service.validation.ProductValidationService;
import com.example.techspot.modules.users.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCreateAction {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final ProductValidationService validationService;
	private final CategoryProductProvider categoryProvider;
	private final UserProvider userRepository;
	private final ProductResponseFactory productResponseFactory;

	public ProductResponse create(ProductCreateRequest req, UUID userId) {
		validationService.validateProductUniqueForUser(req.productName(), userId);

		User user = userRepository.findById(userId);
		Category category = categoryProvider.findLeafCategoryById(req.categoryId());

		Product product = productMapper.toProduct(req);
		product.setUser(user);
		product.setCategory(category);

		Product saved = productRepository.save(product);
		return productResponseFactory.fromEntity(saved);
	}
}

