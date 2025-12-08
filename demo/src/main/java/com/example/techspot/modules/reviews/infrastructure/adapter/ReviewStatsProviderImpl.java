package com.example.techspot.modules.reviews.infrastructure.adapter;

import com.example.techspot.modules.api.review.ReviewStatsProvider;
import com.example.techspot.modules.reviews.infrastructure.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewStatsProviderImpl implements ReviewStatsProvider {

	private final ReviewRepository reviewRepository;

	@Override
	public Long countReviewsForUser(UUID userId){
		log.info("Получаем количество отзывов у пользователя {}",userId);
		long totalReviews = reviewRepository.countByUserId(userId);
		log.info("Пользователь {} оставил {} отзывов",userId,totalReviews);
		return totalReviews;
	}
}
