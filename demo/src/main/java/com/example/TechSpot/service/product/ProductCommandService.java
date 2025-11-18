package com.example.TechSpot.service.product;


import com.example.TechSpot.dto.product.ProductCreateRequest;
import com.example.TechSpot.dto.product.ProductResponse;
import com.example.TechSpot.dto.product.ProductUpdateRequest;
import com.example.TechSpot.entity.Product;
import com.example.TechSpot.entity.Role;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.exception.product.ProductAccessDeniedException;
import com.example.TechSpot.exception.product.ProductNotFoundException;
import com.example.TechSpot.mapping.ProductMapper;
import com.example.TechSpot.repository.ProductRepository;
import com.example.TechSpot.service.user.UserFinder;
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


	@Transactional
	public ProductResponse createProduct(ProductCreateRequest createRequest, UUID userId){

		log.info("Началось создание товара {}",createRequest.productName());
		User user = userFinder.findById(userId);
		Product product = productMapper.toProduct(createRequest);
		product.setUser(user);
		Product createProduct = productRepository.save(product);
		log.info("Товар {} был успешно создан ",createProduct.getProductName());
		return productMapper.toResponseProduct(createProduct);
	}

	@Transactional
	public void deleteProduct(Long productId, UUID userId) {
		log.info("Удаление товара ID: {} пользователем: {}", productId, userId);

		Product product = productRepository.findById(productId)
				.orElseThrow(ProductNotFoundException::new);

		User user = userFinder.findById(userId);
		boolean canDelete = user.getRoles().contains( Role.ROLE_ADMIN) ||
				product.getUser().getId().equals(userId);

		if (!canDelete) {
			throw new ProductAccessDeniedException();
		}

		productRepository.delete(product);
		log.info("Товар {} удален", productId);
	}

	public boolean reduceQuantity(Long productId, int quantity) {
		int updatedRows = productRepository.reduceQuantity(productId, quantity);
		return updatedRows > 0; // true если уменьшили, false если товара не хватило
	}


	@Transactional
	public ProductResponse updateProduct(Long productId, ProductUpdateRequest request,UUID currentUser) {

		log.info("Начало обновления товара {}",productId);
		Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
		User user = userFinder.findById(currentUser);

		if (!product.getUser().getId().equals(user.getId())){
			log.warn("Данный товар не пре надлежит данному пользователю {}",currentUser);
			throw new ProductAccessDeniedException();
		}

		productMapper.updateProduct(request,product);

		Product save = productRepository.save(product);

		log.info("Товар был успешно обновлен {}",save.getId());
		return productMapper.toResponseProduct(save);
	}
}


