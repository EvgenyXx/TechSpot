package com.example.techspot.modules.products.application.image;


import com.example.techspot.modules.products.domain.entity.Product;
import com.example.techspot.modules.products.domain.entity.ProductImage;
import com.example.techspot.modules.products.domain.service.query.ProductEntityQueryService;
import com.example.techspot.modules.products.infrastructure.repository.ProductImageRepository;
import com.example.techspot.modules.products.infrastructure.storage.LocalImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {

	private final ProductEntityQueryService productEntityQueryService;
	private final ProductImageRepository productImageRepository;
	private final LocalImageStorageService storageService;

	/**
	 * Привязка списка картинок к товару
	 */
	@Transactional
	public void attachToProduct(Long productId, List<String> imageUrls) {
		Product product = productEntityQueryService.findById(productId);

		for (String url : imageUrls) {
			ProductImage image = new ProductImage();
			image.setImageUrl(url);
			image.setProduct(product);
			productImageRepository.save(image);
		}
	}

	/**
	 * Удаление картинки и файла
	 */
	@Transactional
	public void deleteProductImage(Long imageId) {
		ProductImage image = productImageRepository.findById(imageId)
				.orElseThrow(() -> new RuntimeException("Image not found"));

		storageService.deleteProductImage(image.getImageUrl());
		productImageRepository.delete(image);
	}
}
