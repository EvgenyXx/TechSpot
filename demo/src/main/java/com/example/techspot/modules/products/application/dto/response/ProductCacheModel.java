package com.example.techspot.modules.products.application.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductCacheModel(
		Long id,
		String productName,
		BigDecimal price,
		Integer quantity,
		String description,
		String productCategory,
		String customerName,
		String sellerEmail,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		Boolean isAvailable,
		String categoryDisplayName,
		List<String> imageUrls
) implements Serializable {}
