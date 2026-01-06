package com.example.techspot.modules.products.infrastructure.storage;

import com.example.techspot.core.config.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalImageStorageService {

	private final StorageProperties properties;

	public List<String> saveProductImages(Long productId, List<MultipartFile> files) {
		try {
			Path productDir = Paths.get(
					properties.getLocalPath(),
					"products",
					productId.toString()
			);
			Files.createDirectories(productDir);

			List<String> imageUrls = new ArrayList<>();

			for (MultipartFile file : files) {
				if (file.isEmpty()) continue;

				String filename = UUID.randomUUID() + ".jpg";
				Path targetPath = productDir.resolve(filename);

				Files.copy(
						file.getInputStream(),
						targetPath,
						StandardCopyOption.REPLACE_EXISTING
				);

				imageUrls.add("/images/products/" + productId + "/" + filename);
			}

			return imageUrls;

		} catch (IOException e) {
			throw new RuntimeException("Failed to save images", e);
		}
	}

	public void deleteProductImage(String imageUrl) {
		try {
			// /images/products/1/file.jpg → products/1/file.jpg
			String relativePath = imageUrl.replaceFirst("^/images/", "");
			Path fullPath = Paths.get(properties.getLocalPath(), relativePath);

			if (Files.exists(fullPath)) {
				Files.delete(fullPath);
			}

		} catch (IOException e) {
			throw new RuntimeException("Failed to delete image: " + imageUrl, e);
		}
	}
}
