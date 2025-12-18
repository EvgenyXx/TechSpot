package com.example.techspot.modules.api.review;

import java.util.UUID;

public interface ReviewStatsProvider {

	Long countReviewsForUser(UUID userId);
}
