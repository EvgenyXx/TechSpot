package com.example.TechSpot.service.review;


import com.example.TechSpot.entity.Review;
import com.example.TechSpot.exception.review.ReviewNotFoundException;
import com.example.TechSpot.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewFinder {

	private final ReviewRepository reviewRepository;


	public Long countReviewsForUser(UUID userId){
		log.info("Получаем количество отзывов у пользователя {}",userId);
		long totalReviews = reviewRepository.countByUserId(userId);
		log.info("Пользователь {} оставил {} отзывов",userId,totalReviews);
		return totalReviews;
	}

	public Review findById (Long reviewId){
		return reviewRepository.findById(reviewId)
				.orElseThrow(ReviewNotFoundException::new);
	}
}
