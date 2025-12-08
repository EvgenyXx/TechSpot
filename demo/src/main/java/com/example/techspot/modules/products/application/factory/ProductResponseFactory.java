package com.example.techspot.modules.products.application.factory;

import com.example.techspot.modules.products.application.dto.response.ProductCacheModel;
import com.example.techspot.modules.products.application.dto.response.ProductResponse;
import com.example.techspot.modules.products.domain.entity.Product;
import com.example.techspot.modules.products.application.mapper.ProductMapper;
import com.example.techspot.modules.products.domain.service.validation.ProductPricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductResponseFactory {

	private final ProductPricingService pricing;

	public ProductResponse build(ProductCacheModel c, BigDecimal discounted) {
		return new ProductResponse(
				c.id(),
				c.productName(),
				c.price(),
				discounted,
				c.quantity(),
				c.description(),
				c.productCategory(),
				c.customerName(),
				c.sellerEmail(),
				c.createdAt(),
				c.updatedAt(),
				c.isAvailable(),
				c.categoryDisplayName()
		);
	}

	public ProductResponse fromEntity(Product p) {
		BigDecimal discounted = pricing.calculate(p);

		return new ProductResponse(
				p.getId(),
				p.getProductName(),
				p.getPrice(),
				discounted,
				p.getQuantity(),
				p.getDescription(),
				p.getCategory().getSlug(),
				p.getUser().getFirstname(),
				p.getUser().getEmail(),
				p.getCreatedAt(),
				p.getUpdatedAt(),
				p.isAvailable(),
				p.getCategory().getName()
		);
	}
}
