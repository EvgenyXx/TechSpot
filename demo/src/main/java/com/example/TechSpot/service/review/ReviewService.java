package com.example.TechSpot.service.review;


import com.example.TechSpot.dto.review.ReviewCreateRequest;
import com.example.TechSpot.dto.review.ReviewResponse;
import com.example.TechSpot.entity.Product;
import com.example.TechSpot.entity.Review;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.exception.DuplicateReviewException;
import com.example.TechSpot.exception.ReviewAccessDeniedException;
import com.example.TechSpot.exception.ReviewNotFoundException;
import com.example.TechSpot.mapping.ReviewMapper;
import com.example.TechSpot.repository.ReviewRepository;
import com.example.TechSpot.service.product.ProductFinder;
import com.example.TechSpot.service.user.UserFinder;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor // todo add methods in controller
public class ReviewService {

	private final UserFinder userFinder;
	private final ProductFinder productFinder;
	private final ReviewRepository reviewRepository;
	private final ReviewMapper reviewMapper;


	@Transactional
	public ReviewResponse addReview(Long productId, ReviewCreateRequest request, UUID userId) {
		log.info("Оставление отзыва товару {}",productId);
		User user = userFinder.findById(userId);
		Product product = productFinder.findById(productId);
		checkingReviewDuplicateForUserOnProduct(userId,productId);
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

	private void  checkingReviewDuplicateForUserOnProduct(UUID userId,Long productId){
		log.info("Проверка отзыва пользователя {} на товар {}",userId,productId);
		if (reviewRepository.existsByProductIdAndUserId(productId,userId)){
			log.warn("Попытка оставить отзыв на товар где уже есть отзыв {}",productId);
			throw new DuplicateReviewException();
		}
	}

	@Transactional
	public void markReviewAsHelpful(Long reviewId, UUID userId) {
		Review review = findById(reviewId);
		checkUserHasNotVoted(reviewId,userId);
		review.setHelpfulCount(review.getHelpfulCount() + 1);
		reviewRepository.save(review);
		log.info("Пользователь {} отметил отзыв {} как полезный", userId, reviewId);
	}

	private Review findById (Long reviewId){
		return reviewRepository.findById(reviewId)
				.orElseThrow(ReviewNotFoundException::new);
	}

	private void checkUserHasNotVoted(Long reviewId, UUID userId){
		if (reviewRepository.existsByIdAndUserId(reviewId, userId)){
			throw new DuplicateReviewException();
		}
	}

	@Transactional(readOnly = true)
	public List<ReviewResponse> getProductReviews(Long productId) {
		log.info("Получение отзывов для товара ID: {}", productId);

		List<ReviewResponse> reviews = reviewRepository.findByProductId(productId)
				.stream()
				.map(reviewMapper::toReviewResponse)
				.toList();

		log.info("Найдено {} отзывов для товара ID: {}", reviews.size(), productId);
		return reviews;
	}

	@Transactional(readOnly = true)
	public void deleteReview(Long reviewId, UUID userId) {
		log.info("Удаление отзыва ID: {} пользователем: {}", reviewId, userId);

		Review review = findById(reviewId);

		if (!review.getUser().getId().equals(userId)) {
			log.warn("Попытка удалить чужой отзыв. Отзыв: {}, Пользователь: {}", reviewId, userId);
			throw new ReviewAccessDeniedException();
		}

		reviewRepository.delete(review);
		log.info("Отзыв ID: {} успешно удален", reviewId);
	}

	@Transactional(readOnly = true)
	public List<ReviewResponse> getUserReviews(UUID userId) {
		log.info("Получение отзывов пользователя ID: {}", userId);

		List<ReviewResponse> reviews = reviewRepository.findByUserId(userId)
				.stream()
				.map(reviewMapper::toReviewResponse)
				.toList();

		log.info("Найдено {} отзывов пользователя ID: {}", reviews.size(), userId);
		return reviews;
	}


}
