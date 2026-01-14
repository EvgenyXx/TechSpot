package com.example.techspot.modules.products.domain.entity;


import com.example.techspot.entity.BaseEntity;
import com.example.techspot.modules.categories.domain.entity.Category;
import com.example.techspot.modules.users.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Product extends BaseEntity  {

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

	@OneToMany(
			mappedBy = "product",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<ProductImage> images = new ArrayList<>();

	public boolean isAvailable(){
		return this.getQuantity() > 0;
	}
}
