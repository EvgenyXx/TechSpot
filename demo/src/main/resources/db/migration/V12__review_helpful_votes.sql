CREATE TABLE review_helpful_votes (
    review_id BIGINT NOT NULL,
    user_id UUID NOT NULL,
    PRIMARY KEY (review_id, user_id),
    CONSTRAINT fk_review_helpful_votes_review
        FOREIGN KEY (review_id) REFERENCES reviews (id) ON DELETE CASCADE,
    CONSTRAINT fk_review_helpful_votes_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);