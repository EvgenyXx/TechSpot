package com.example.techspot.modules.products.api;

import com.example.techspot.common.constants.ApiPaths;
import com.example.techspot.modules.products.application.image.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.techspot.common.constants.SecurityRoles.IS_SELLER;
import static com.example.techspot.common.constants.SecurityRoles.IS_SELLER_OR_ADMIN;

@RestController
@RequestMapping(ApiPaths.PRODUCT_BASE)
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Product Images", description = "API для управления изображениями товаров")
public class ProductImageController {

	private final ProductImageService productImageService;

	@Operation(
			summary = "Добавить изображения к товару",
			description = """
                    Загружает одну или несколько картинок для товара.
                    
                    **Доступ:**
                    - Только продавец (владелец товара)
                    """
	)
	@ApiResponse(responseCode = "200", description = "Изображения успешно добавлены")
	@ApiResponse(responseCode = "403", description = "Недостаточно прав")
	@PostMapping(ApiPaths.PRODUCT_IMAGES)
	@PreAuthorize(IS_SELLER_OR_ADMIN)
	public ResponseEntity<List<String>> uploadImages(
			@PathVariable Long productId,
			@RequestParam("image") List<MultipartFile> images
	) {
		log.info("Upload images for product {}", productId);
		return ResponseEntity.ok(
				productImageService.addImages(productId, images)
		);
	}

	@Operation(
			summary = "Удалить изображение товара",
			description = """
                    Удаляет изображение товара.
                    
                    **Доступ:**
                    - Продавец (владелец товара)
                    - Администратор
                    """
	)
	@ApiResponse(responseCode = "204", description = "Изображение удалено")
	@ApiResponse(responseCode = "404", description = "Изображение не найдено")
	@DeleteMapping(ApiPaths.PRODUCT_IMAGE_ID)
	@PreAuthorize(IS_SELLER_OR_ADMIN)
	public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
		log.info("Delete image {}", imageId);
		productImageService.deleteImage(imageId);
		return ResponseEntity.noContent().build();
	}
}
