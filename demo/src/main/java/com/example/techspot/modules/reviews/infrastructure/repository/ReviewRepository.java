package com.example.techspot.modules.reviews.infrastructure.repository;

import com.example.techspot.modules.reviews.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findByProductId(Long productId);
	boolean existsByProductIdAndUserId(Long productId, UUID userId);

	boolean existsByIdAndUserId(Long reviewId, UUID userId);

	List<Review>findByUserId(UUID userId);

	long countByUserId(UUID userId);





}