package com.example.techspot.modules.orders.domain.entity;

import com.example.techspot.entity.BaseEntity;
import com.example.techspot.modules.users.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true) // ← ИСПРАВЛЕНО
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String orderNumber;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false) // ← @JoinColumn вместо @Column
	private User user;

	@Column(name = "total_price", nullable = false)
	private BigDecimal totalPrice;

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status", nullable = false)
	private OrderStatus orderStatus;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // ← убрал @Column
	private Set<OrderItems> orderItems;
}