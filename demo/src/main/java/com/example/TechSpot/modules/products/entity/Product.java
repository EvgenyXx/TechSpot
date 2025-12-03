package com.example.TechSpot.modules.products.entity;


import com.example.TechSpot.entity.BaseEntity;
import com.example.TechSpot.modules.categories.entity.Category;
import com.example.TechSpot.modules.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Product extends BaseEntity   {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include // ← Включаем только ID
	private Long id;


	@Column(name = "product_name",nullable = false)
	private String productName;

	@Column(name = "price",nullable = false)
	private BigDecimal price;

	@Column(name = "quantity",nullable = false)
	private int quantity;

	@Column(name = "description",nullable = false,length = 1000)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",nullable = false)
	private User user;
}
