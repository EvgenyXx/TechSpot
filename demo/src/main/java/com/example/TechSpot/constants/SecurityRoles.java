package com.example.TechSpot.constants;

public final class SecurityRoles {

	private SecurityRoles() {
		// utility class
	}

	public static final String USER = "USER";
	public static final String ADMIN = "ADMIN";
	public static final String MODERATOR = "MODERATOR";
	public static final String SELLER = "SELLER";  // ✅ ДОБАВИЛ SELLER

	// PreAuthorize выражения
	public static final String IS_USER = "hasRole('" + USER + "')";
	public static final String IS_ADMIN = "hasRole('" + ADMIN + "')";
	public static final String IS_MODERATOR = "hasRole('" + MODERATOR + "')";
	public static final String IS_SELLER = "hasRole('" + SELLER + "')";  // ✅ ДОБАВИЛ SELLER

	// Комбинированные выражения
	public static final String IS_USER_OR_ADMIN = "hasAnyRole('" + USER + "', '" + ADMIN + "')";
	public static final String IS_SELLER_OR_ADMIN = "hasAnyRole('" + SELLER + "', '" + ADMIN + "')";  // ✅ ДОБАВИЛ
	public static final String IS_USER_OR_SELLER = "hasAnyRole('" + USER + "', '" + SELLER + "')";    // ✅ ДОБАВИЛ
	public static final String IS_USER_OR_SELLER_OR_ADMIN = "hasAnyRole('" + USER + "', '" + SELLER + "', '" + ADMIN + "')";  // ✅ ДОБАВИЛ
}