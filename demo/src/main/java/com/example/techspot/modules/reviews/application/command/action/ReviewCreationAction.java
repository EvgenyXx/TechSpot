package com.example.techspot.modules.reviews.application.command.action;

import com.example.techspot.modules.api.product.ProductProvider;
import com.example.techspot.modules.api.user.UserRepositoryProvider;
import com.example.techspot.modules.products.domain.entity.Product;
import com.example.techspot.modules.reviews.application.dto.ReviewCreateRequest;
import com.example.techspot.modules.reviews.domain.entity.Review;
import com.example.techspot.modules.reviews.application.factory.ReviewFactory;
import com.example.techspot.modules.reviews.domain.service.ReviewValidationService;
import com.example.techspot.modules.reviews.infrastructure.repository.ReviewRepository;
import com.example.techspot.modules.users.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewCreationAction {
	private final UserRepositoryProvider users;
	private final ProductProvider products;
	private final ReviewValidationService validation;
	private final ReviewRepository reviews;
	private final ReviewFactory factory;

	public Review create(Long productId, UUID userId, ReviewCreateRequest req) {

		User user = users.findById(userId);
		Product product = products.findById(productId);

		validation.checkingReviewDuplicateForUserOnProduct(userId, productId);

		Review review = factory.create(user, product, req);

		return reviews.save(review);
	}
}
