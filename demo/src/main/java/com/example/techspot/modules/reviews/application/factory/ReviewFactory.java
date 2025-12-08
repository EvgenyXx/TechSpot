package com.example.techspot.modules.reviews.application.factory;

import com.example.techspot.modules.products.domain.entity.Product;
import com.example.techspot.modules.reviews.application.dto.ReviewCreateRequest;
import com.example.techspot.modules.reviews.domain.entity.Review;
import com.example.techspot.modules.users.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewFactory {

	public Review create(User user, Product product, ReviewCreateRequest request) {
		return Review.builder()
				.user(user)
				.product(product)
				.comment(request.comment())
				.rating(request.rating())
				.build();
	}
}
