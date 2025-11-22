package com.example.TechSpot.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true) // ← ИСПРАВЛЕНО
public class User extends BaseEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "firstname", nullable = false)
	private String firstname;


	@Column(name = "lastname", nullable = false)
	private String lastname;


	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "phone_number",
			unique = true,
			nullable = false,
			length = 12
	)
	private String phoneNumber;

	@Column(name = "hash_password",
			nullable = false,
			length = 60
	)
	private String hashPassword;


	@ManyToMany
	@JoinTable(
			name = "user_roles", // ← Промежуточная таблица
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();


	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Product> products = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cart_id")  // ✅ внешний ключ в таблице users
	private Cart cart;



	@Column(name = "is_active", nullable = false)
	private boolean isActive ;


	@Column(name = "password_reset_code",unique = true)
	private String passwordResetCode;

	@Column(name = "reset_code_expiry")
	private LocalDateTime resetCodeExpiry;


}
