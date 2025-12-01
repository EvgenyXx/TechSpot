package com.example.TechSpot.modules.users.entity;

import com.example.TechSpot.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Role extends BaseEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(name = "name",unique = true,nullable = false)
	private String name;


	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<>();
}
