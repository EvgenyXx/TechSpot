package com.example.techspot.modules.cart.domain.entity;


import com.example.techspot.entity.BaseEntity;
import com.example.techspot.modules.products.domain.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "cart_items")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true) // ← ИСПРАВЛЕНО
public class CartItems extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id", nullable = false)
	private Cart cart;

	@Column(nullable = false)
	private int quantity;

	@Column(name = "item_price", nullable = false)
	private BigDecimal itemPrice;


	public BigDecimal getTotalPrice(){
		return itemPrice.multiply(BigDecimal.valueOf(quantity));
	}


}
