package com.example.TechSpot.modules.reviews.service.command;


import com.example.TechSpot.core.config.CacheNames;
import com.example.TechSpot.modules.api.product.ProductRepositoryProvider;
import com.example.TechSpot.modules.api.user.UserRepositoryProvider;
import com.example.TechSpot.modules.products.entity.Product;
import com.example.TechSpot.modules.reviews.dto.ReviewCreateRequest;
import com.example.TechSpot.modules.reviews.dto.ReviewResponse;
import com.example.TechSpot.modules.reviews.entity.Review;
import com.example.TechSpot.modules.reviews.exception.ReviewAccessDeniedException;
import com.example.TechSpot.modules.reviews.mapper.ReviewMapper;
import com.example.TechSpot.modules.reviews.repository.ReviewRepository;
import com.example.TechSpot.modules.reviews.service.query.ReviewFinder;
import com.example.TechSpot.modules.reviews.service.validation.ReviewValidationService;
import com.example.TechSpot.modules.users.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandService {

	private final UserRepositoryProvider userRepositoryProvider;
	private final ProductRepositoryProvider productRepositoryProvider;
	private final ReviewRepository reviewRepository;
	private final ReviewMapper reviewMapper;
	private final ReviewFinder reviewFinder;
	private final ReviewValidationService reviewValidationService;


	@CachePut(value = CacheNames.REVIEWS, key = "#productId")
	public ReviewResponse addReview(Long productId, ReviewCreateRequest request, UUID userId) {
		log.info("Оставление отзыва товару {}",productId);
		User user = userRepositoryProvider.findById(userId);
		Product product = productRepositoryProvider.findById(productId);
		reviewValidationService.checkingReviewDuplicateForUserOnProduct(userId,productId);
		Review review = Review.builder()
				.user(user)
				.product(product)
				.comment(request.comment())
				.helpfulCount(0)
				.rating(request.rating())
				.build();
		Review save = reviewRepository.save(review);
		log.info("Отзыв был успешно оставлен товару {}",product);
		return  reviewMapper.toReviewResponse(save);
	}




	@CachePut(value = CacheNames.REVIEWS,key = "#reviewId")
	public void markReviewAsHelpful(Long reviewId, UUID userId) {
		Review review = reviewFinder.findById(reviewId);
		reviewValidationService.checkUserHasNotVoted(reviewId,userId);
		review.setHelpfulCount(review.getHelpfulCount() + 1);
		reviewRepository.save(review);
		log.info("Пользователь {} отметил отзыв {} как полезный", userId, reviewId);
	}





	@CacheEvict(value = "product_reviews", key = "#review.product.id")
	public void deleteReview(Long reviewId, UUID userId) {
		log.info("Удаление отзыва ID: {} пользователем: {}", reviewId, userId);

		Review review = reviewFinder.findById(reviewId);

		if (!review.getUser().getId().equals(userId)) {
			log.warn("Попытка удалить чужой отзыв. Отзыв: {}, Пользователь: {}", reviewId, userId);
			throw new ReviewAccessDeniedException();
		}

		reviewRepository.delete(review);
		log.info("Отзыв ID: {} успешно удален", reviewId);
	}




}
