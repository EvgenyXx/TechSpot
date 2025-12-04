package com.example.TechSpot.modules.discount;

import com.example.TechSpot.modules.categories.entity.Category;
import com.example.TechSpot.modules.products.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "discounts")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Discount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Тип скидки
	@Enumerated(EnumType.STRING)
	private DiscountType type; // PRODUCT, CATEGORY, GLOBAL

	// Процент скидки
	private BigDecimal percentage; // например 10 = 10%

	// Фиксированная скидка (если хочешь)
	private BigDecimal fixedAmount;

	// Даты действия скидки
	private LocalDateTime startsAt;
	private LocalDateTime endsAt;

	// СВЯЗИ (необязательные)
	@ManyToOne
	private Product product; // скидка на товар

	@ManyToOne
	private Category category; // скидка на категорию

	private boolean active;
}
