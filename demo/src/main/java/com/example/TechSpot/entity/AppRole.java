package com.example.TechSpot.entity;

public enum AppRole {
	ROLE_ADMIN,
	ROLE_USER,
	ROLE_MODERATOR,
	ROLE_SELLER;

	// Можно добавить хелпер методы
	public String getAuthority() {
		return this.name();
	}
}