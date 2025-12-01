package com.example.TechSpot.modules.reviews.service.query;


import com.example.TechSpot.modules.reviews.exception.ReviewNotFoundException;
import com.example.TechSpot.modules.reviews.entity.Review;
import com.example.TechSpot.modules.reviews.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
