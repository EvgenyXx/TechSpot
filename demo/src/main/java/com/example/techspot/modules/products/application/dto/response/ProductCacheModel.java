package com.example.techspot.modules.products.application.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
		String categoryDisplayName
) implements Serializable {}
