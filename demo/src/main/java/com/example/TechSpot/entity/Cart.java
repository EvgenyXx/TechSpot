package com.example.TechSpot.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;

import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cart extends BaseEntity{

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@OneToOne
	private User user;


	@OneToMany(cascade = CascadeType.ALL,mappedBy = "cart",orphanRemoval = true)
	private Set<CartItems>cartItems = new HashSet<>();

	@Column(name = "total_price",nullable = false)
	private BigDecimal totalPrice = BigDecimal.valueOf(0.0);




}
