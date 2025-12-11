package com.example.techspot.modules.products.application.command;

import com.example.techspot.modules.api.user.UserProvider;
import com.example.techspot.modules.products.application.dto.request.ProductUpdateRequest;
import com.example.techspot.modules.products.application.dto.response.ProductResponse;
import com.example.techspot.modules.products.domain.entity.Product;
import com.example.techspot.modules.products.application.exception.ProductAccessDeniedException;
import com.example.techspot.modules.products.application.mapper.ProductMapper;
import com.example.techspot.modules.products.infrastructure.repository.ProductRepository;
import com.example.techspot.modules.products.domain.service.query.ProductEntityQueryService;
import com.example.techspot.modules.users.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductUpdateAction {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final ProductEntityQueryService productQuery;
	private final UserProvider userRepository;

	public ProductResponse update(Long id, ProductUpdateRequest req, UUID userId) {

		Product product = productQuery.findById(id);
		User user = userRepository.findById(userId);

		if (!product.getUser().getId().equals(user.getId())) {
			throw new ProductAccessDeniedException();
		}

		productMapper.updateProduct(req, product);

		Product saved = productRepository.save(product);
		return productMapper.toResponseProductWithCalculatedFields(saved, null);
	}
}
