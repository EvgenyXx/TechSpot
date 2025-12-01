package com.example.TechSpot.modules.reviews.entity;

import com.example.TechSpot.entity.BaseEntity;
import com.example.TechSpot.modules.products.entity.Product;
import com.example.TechSpot.modules.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(nullable = false)
	private Integer rating; // 1-5

	@Column(columnDefinition = "TEXT", nullable = false)
	private String comment;

	@Column(name = "helpful_count")
	private Integer helpfulCount = 0;
}