package com.example.techspot.modules.categories.domain.entity;

import com.example.techspot.entity.BaseEntity;
import com.example.techspot.modules.products.domain.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true) // ← ДОБАВЬ
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name",unique = true,nullable = false)
	private String name;

	@Column(name = "slug",unique = true,nullable = false)
	private String slug;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Category parent;


	@OneToMany(mappedBy = "parent")
	private List<Category> children = new ArrayList<>();

	@OneToMany(mappedBy = "category")
	private List<Product> products = new ArrayList<>();
}