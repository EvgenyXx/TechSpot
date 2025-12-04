package com.example.TechSpot.modules.products.service.command;

import com.example.TechSpot.core.config.CacheNames;
import com.example.TechSpot.modules.api.category.CategoryProductProvider;
import com.example.TechSpot.modules.api.user.UserRepositoryProvider;
import com.example.TechSpot.modules.api.user.UserSecurityProvider;
import com.example.TechSpot.modules.products.dto.request.ProductCreateRequest;
import com.example.TechSpot.modules.products.dto.response.ProductResponse;
import com.example.TechSpot.modules.products.dto.request.ProductUpdateRequest;
import com.example.TechSpot.modules.categories.entity.Category;
import com.example.TechSpot.modules.products.entity.Product;
import com.example.TechSpot.modules.products.service.query.ProductEntityQueryService;
import com.example.TechSpot.modules.products.service.validation.ProductValidationService;
import com.example.TechSpot.modules.users.entity.User;
import com.example.TechSpot.modules.products.exception.ProductAccessDeniedException;
import com.example.TechSpot.modules.products.mapper.ProductMapper;
import com.example.TechSpot.modules.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductCommandService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final CategoryProductProvider categoryProductProvider;
	private final ProductValidationService productValidationService;
	private final UserSecurityProvider userSecurityProvider;
	private final ProductEntityQueryService productEntityQueryService;
	private final UserRepositoryProvider userRepositoryProvider;


	@CachePut(value = CacheNames.PRODUCTS,key = "#result.id")
	@Transactional
	public ProductResponse createProduct(ProductCreateRequest createRequest, UUID userId) {
		log.info("Началось создание товара {}", createRequest.productName());
		productValidationService.validateProductUniqueForUser(createRequest.productName(), userId);
		User user = userRepositoryProvider.findById(userId);
		Category category = categoryProductProvider.findLeafCategoryById(createRequest.categoryId());
		Product product = productMapper.toProduct(createRequest);
		product.setUser(user);
		product.setCategory(category);
		Product createProduct = productRepository.save(product);
		log.info("Товар {} был успешно создан ", createProduct.getProductName());
		return productMapper.toResponseProductWithCalculatedFields(createProduct,null);
	}


	@CacheEvict(value = CacheNames.PRODUCTS,key = "#productId")
	@Transactional
	public void deleteProduct(Long productId, UUID userId) {
		log.info("Удаление товара ID: {} пользователем: {}", productId, userId);

		Product product = productEntityQueryService.findById(productId);

		if (!userSecurityProvider.canDeleteProduct(userId, product.getUser().getId())) {
			throw new ProductAccessDeniedException();
		}

		productRepository.delete(product);
		log.info("Товар {} удален", productId);
	}


	@CachePut(value = CacheNames.PRODUCTS,key = "#productId")
	@Transactional
	public ProductResponse updateProduct(Long productId, ProductUpdateRequest request, UUID currentUser) {
		log.info("Начало обновления товара {}", productId);
		Product product = productEntityQueryService.findById(productId);
		User user = userRepositoryProvider.findById(currentUser);

		if (!product.getUser().getId().equals(user.getId())) {
			log.warn("Данный товар не принадлежит данному пользователю {}", currentUser);
			throw new ProductAccessDeniedException();
		}

		productMapper.updateProduct(request, product);
		Product saveProduct = productRepository.save(product);

		log.info("Товар был успешно обновлен {}", saveProduct.getId());
		return productMapper.toResponseProductWithCalculatedFields(saveProduct,null);
	}
}