package com.example.techspot.modules.discount.domain.entity;

import com.example.techspot.modules.categories.domain.entity.Category;
import com.example.techspot.modules.products.domain.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private DiscountType type;

	@Column(name = "percentage", precision = 10, scale = 2)
	private BigDecimal percentage;

	@Column(name = "fixed_amount", precision = 10, scale = 2)
	private BigDecimal fixedAmount;

	@Column(name = "starts_at")
	private LocalDateTime startsAt;

	@Column(name = "ends_at")
	private LocalDateTime endsAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "min_quantity")
	private Integer minQuantity;

	@Column(nullable = false)
	private boolean active;
}
