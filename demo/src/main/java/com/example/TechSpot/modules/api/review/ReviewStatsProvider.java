package com.example.TechSpot.modules.api.review;

import java.util.UUID;

public interface ReviewStatsProvider {

	Long countReviewsForUser(UUID userId);
}
