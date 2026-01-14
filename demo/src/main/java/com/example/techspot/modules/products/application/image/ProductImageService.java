package com.example.techspot.modules.products.application.image;

import com.example.techspot.modules.products.application.exception.ProductImageNotFound;
import com.example.techspot.modules.products.domain.entity.Product;
import com.example.techspot.modules.products.domain.entity.ProductImage;
import com.example.techspot.modules.products.domain.service.query.ProductEntityQueryService;
import com.example.techspot.modules.products.infrastructure.repository.ProductImageRepository;
import com.example.techspot.modules.products.infrastructure.storage.LocalImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {

	private final ProductEntityQueryService productQueryService;
	private final ProductImageRepository productImageRepository;
	private final LocalImageStorageService storageService;

	/**
	 * Загрузка изображений и привязка к товару
	 */
	@Transactional
	public List<String> addImages(Long productId, List<MultipartFile> files) {

		Product product = productQueryService.findById(productId);

		List<String> imageUrls = storageService.saveProductImages(productId, files);

		for (String url : imageUrls) {
			ProductImage image = new ProductImage();
			image.setImageUrl(url);
			image.setProduct(product);
			productImageRepository.save(image);
		}

		return imageUrls;
	}

	/**
	 * Удаление изображения
	 */
	@Transactional
	public void deleteImage(Long imageId) {

		ProductImage image = productImageRepository.findById(imageId)
				.orElseThrow(ProductImageNotFound::new);

		storageService.deleteProductImage(image.getImageUrl());
		productImageRepository.delete(image);
	}
}
