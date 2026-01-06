package com.example.techspot.modules.products.api;

import com.example.techspot.modules.products.application.image.ProductImageService;
import com.example.techspot.modules.products.infrastructure.storage.LocalImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductImageController {

	private final LocalImageStorageService storageService;
	private final ProductImageService productImageService;

	/**
	 * Загрузка ОДНОГО ИЛИ НЕСКОЛЬКИХ фото к товару
	 * form-data:
	 * image: file1
	 * image: file2
	 */
	@PostMapping("/{productId}/images")
	public ResponseEntity<List<String>> uploadImages(
			@PathVariable Long productId,
			@RequestParam("image") List<MultipartFile> images
	) {
		List<String> imageUrls = storageService.saveProductImages(productId, images);
		productImageService.attachToProduct(productId, imageUrls);
		return ResponseEntity.ok(imageUrls);
	}

	/**
	 * Удаление конкретной картинки по ID
	 */
	@DeleteMapping("/images/{imageId}")
	public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
		productImageService.deleteProductImage(imageId);
		return ResponseEntity.noContent().build();
	}
}
