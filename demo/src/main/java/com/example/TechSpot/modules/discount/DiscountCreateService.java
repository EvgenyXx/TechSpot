package com.example.TechSpot.modules.discount;

import com.example.TechSpot.modules.categories.repository.CategoryRepository;
import com.example.TechSpot.modules.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountCreateService {

	private final DiscountRepository discountRepository;
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	public Discount create(CreateDiscountRequest request) {

		Discount.DiscountBuilder builder = Discount.builder()
				.type(request.type())
				.percentage(request.percentage())
				.fixedAmount(request.fixedAmount())
				.startsAt(request.startsAt())
				.endsAt(request.endsAt())
				.active(request.active());

		if (request.productId() != null) {
			builder.product(productRepository.findById(request.productId())
					.orElseThrow(() -> new RuntimeException("Product not found")));
		}

		if (request.categoryId() != null) {
			builder.category(categoryRepository.findById(request.categoryId())
					.orElseThrow(() -> new RuntimeException("Category not found")));
		}

		return discountRepository.save(builder.build());
	}
}