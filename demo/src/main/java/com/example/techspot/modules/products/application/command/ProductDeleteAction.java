package com.example.techspot.modules.products.application.command;

import com.example.techspot.modules.products.domain.entity.Product;
import com.example.techspot.modules.products.domain.service.command.ProductOwnershipService;
import com.example.techspot.modules.products.infrastructure.repository.ProductRepository;
import com.example.techspot.modules.products.domain.service.query.ProductEntityQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductDeleteAction {

	private final ProductRepository productRepository;
	private final ProductEntityQueryService productQuery;
	private final ProductOwnershipService ownershipService;

	public void delete(Long productId, UUID userId) {
		Product product = productQuery.findById(productId);

		ownershipService.checkDeletePermission(userId, product.getUser().getId());

		productRepository.delete(product);
	}
}
