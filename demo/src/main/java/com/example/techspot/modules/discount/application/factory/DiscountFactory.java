package com.example.techspot.modules.discount.application.factory;


import com.example.techspot.modules.api.category.CategoryProvider;
import com.example.techspot.modules.api.product.ProductProvider;
import com.example.techspot.modules.categories.domain.entity.Category;
import com.example.techspot.modules.discount.application.dto.CreateDiscountRequest;
import com.example.techspot.modules.discount.domain.entity.Discount;
import com.example.techspot.modules.products.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountFactory {
	private final ProductProvider productProvider;
	private final CategoryProvider categoryProvider;

	public Discount create(CreateDiscountRequest request) {
		Product product = productProvider.findById(request.productId());
//		Category category = categoryProvider.findById(request.categoryId());

		return Discount.builder()
				.type(request.type())
				.percentage(request.percentage())
				.fixedAmount(request.fixedAmount())
				.startsAt(request.startsAt())
				.endsAt(request.endsAt())
				.active(request.active())
				.product(product)
				.category(null)
				.minQuantity(request.minQuantity())
				.build();
	}
}