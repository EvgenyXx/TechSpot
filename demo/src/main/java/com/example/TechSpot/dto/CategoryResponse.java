package com.example.TechSpot.dto;

public record CategoryResponse(
		Long id,
		String name,
		String slug,
		boolean hasChildren
) {}