package com.example.techspot.modules.reviews.domain.entity;

import com.example.techspot.entity.BaseEntity;
import com.example.techspot.modules.products.domain.entity.Product;
import com.example.techspot.modules.users.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
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


}