package com.example.techspot.modules.reviews.domain.service;


import com.example.techspot.modules.reviews.application.exception.ReviewNotFoundException;
import com.example.techspot.modules.reviews.domain.entity.Review;
import com.example.techspot.modules.reviews.infrastructure.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewFinder {

	private final ReviewRepository reviewRepository;




	public Review findById (Long reviewId){
		return reviewRepository.findById(reviewId)
				.orElseThrow(ReviewNotFoundException::new);
	}
}
