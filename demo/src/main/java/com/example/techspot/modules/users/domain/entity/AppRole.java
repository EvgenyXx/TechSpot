package com.example.techspot.modules.users.domain.entity;

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