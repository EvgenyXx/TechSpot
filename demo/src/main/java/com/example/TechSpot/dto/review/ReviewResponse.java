// ReviewResponse  
package com.example.TechSpot.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Ответ с отзывом")
public record ReviewResponse(
		Long id,
		UUID userId,
		String userName,
		Integer rating,
		String comment,
		Integer helpfulCount,
		LocalDateTime createdAt
) {}