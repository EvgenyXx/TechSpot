package com.example.techspot.modules.reviews.domain.entity;

import com.example.techspot.modules.users.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review_helpful_votes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewHelpfulVote {

	@EmbeddedId
	private ReviewHelpfulVoteId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("reviewId")
	private Review review;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	private User user;
}
