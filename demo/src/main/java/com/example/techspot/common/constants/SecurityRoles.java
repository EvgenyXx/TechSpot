package com.example.techspot.common.constants;

public final class SecurityRoles {

	private SecurityRoles() {
		// utility class
	}

	// ✅ ИСПРАВЬ - ДОБАВЬ ПРЕФИКС ROLE_
	public static final String USER = "ROLE_USER";
	public static final String ADMIN = "ROLE_ADMIN";
	public static final String MODERATOR = "ROLE_MODERATOR";
	public static final String SELLER = "ROLE_SELLER";

	// PreAuthorize выражения (остаются без изменений)
	public static final String IS_USER = "hasRole('" + USER + "')";
	public static final String IS_ADMIN = "hasRole('" + ADMIN + "')";
	public static final String IS_MODERATOR = "hasRole('" + MODERATOR + "')";
	public static final String IS_SELLER = "hasRole('" + SELLER + "')";

	// Комбинированные выражения
	public static final String IS_USER_OR_ADMIN = "hasAnyRole('" + USER + "', '" + ADMIN + "')";
	public static final String IS_SELLER_OR_ADMIN = "hasAnyRole('" + SELLER + "', '" + ADMIN + "')";
	public static final String IS_USER_OR_SELLER = "hasAnyRole('" + USER + "', '" + SELLER + "')";
	public static final String IS_USER_OR_SELLER_OR_ADMIN = "hasAnyRole('" + USER + "', '" + SELLER + "', '" + ADMIN + "')";
}