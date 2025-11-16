package com.example.TechSpot.dto.product;

import java.math.BigDecimal;

public record ProductUpdateRequest(
		String productName,
		String description,
		BigDecimal price,
		Integer quantity,
		Long categoryId
) {}
