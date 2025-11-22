package com.example.TechSpot.service.product;


import com.example.TechSpot.dto.product.ProductCreateRequest;
import com.example.TechSpot.dto.product.ProductResponse;
import com.example.TechSpot.dto.product.ProductUpdateRequest;
import com.example.TechSpot.entity.Category;
import com.example.TechSpot.entity.Product;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.exception.product.ProductAccessDeniedException;
import com.example.TechSpot.exception.product.ProductNotFoundException;
import com.example.TechSpot.mapping.ProductMapper;
import com.example.TechSpot.repository.ProductRepository;
import com.example.TechSpot.service.category.CategoryFinder;
import com.example.TechSpot.service.product.validation.ProductValidationService;
import com.example.TechSpot.service.role.RoleFinder;
import com.example.TechSpot.service.user.query.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductCommandService {


	private final ProductRepository productRepository;
	private final UserFinder userFinder;
	private final ProductMapper productMapper;
	private final CategoryFinder categoryFinder;
	private final RoleFinder roleFinder;
	private final ProductValidationService productValidationService;


	@Transactional
	public ProductResponse createProduct(ProductCreateRequest createRequest, UUID userId) {

		log.info("Началось создание товара {}", createRequest.productName());
		productValidationService.validateProductUniqueForUser(createRequest.productName(), userId);
		User user = userFinder.findById(userId);
		Category category = categoryFinder.findLeafCategoryById(createRequest.categoryId());
		Product product = productMapper.toProduct(createRequest);
		product.setUser(user);
		product.setCategory(category);
		Product createProduct = productRepository.save(product);
		log.info("Товар {} был успешно создан ", createProduct.getProductName());
		return productMapper.toResponseProductWithCalculatedFields(createProduct);
	}

	@Transactional
	public void deleteProduct(Long productId, UUID userId) {
		log.info("Удаление товара ID: {} пользователем: {}", productId, userId);

		Product product = productRepository.findById(productId)
				.orElseThrow(ProductNotFoundException::new);

		User user = userFinder.findById(userId);
		boolean canDelete = user.getRoles().contains(roleFinder.getRoleAdmin()) ||
				product.getUser().getId().equals(userId);

		if (!canDelete) {
			throw new ProductAccessDeniedException();
		}

		productRepository.delete(product);
		log.info("Товар {} удален", productId);
	}




	@Transactional
	public ProductResponse updateProduct(Long productId, ProductUpdateRequest request, UUID currentUser) {

		log.info("Начало обновления товара {}", productId);
		Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
		User user = userFinder.findById(currentUser);

		if (!product.getUser().getId().equals(user.getId())) {
			log.warn("Данный товар не пре надлежит данному пользователю {}", currentUser);
			throw new ProductAccessDeniedException();
		}

		productMapper.updateProduct(request, product);

		Product saveProduct = productRepository.save(product);

		log.info("Товар был успешно обновлен {}", saveProduct.getId());
		return productMapper.toResponseProductWithCalculatedFields(saveProduct);
	}
}


