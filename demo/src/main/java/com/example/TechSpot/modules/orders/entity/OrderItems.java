package com.example.TechSpot.modules.orders.entity;

import com.example.TechSpot.entity.BaseEntity;
import com.example.TechSpot.modules.products.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderItems extends BaseEntity {

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "order_id",nullable = false)
	private Order order;

	@ManyToOne
	@JoinColumn(name = "product_id",nullable = false)
	private Product product;      // Копия данных на момент заказа

	@Column(name = "product_name",nullable = false)
	private String productName;   // Название на момент заказа
	@Column(name = "item_price",nullable = false)
	private BigDecimal itemPrice;     // Цена на момент заказа

	@Column(name = "quantity",nullable = false)
	private Integer quantity;
}
