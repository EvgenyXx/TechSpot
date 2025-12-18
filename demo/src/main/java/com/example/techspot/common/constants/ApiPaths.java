package com.example.techspot.common.constants;

public class ApiPaths {

	private ApiPaths (){

	}

	public static final String API_V1 = "/api/v1";

	// BASE paths для контроллеров
	public static final String AUTH_BASE = API_V1 + "/auth";
	public static final String CART_BASE = API_V1 + "/cart";
	public static final String ORDER_BASE = API_V1 + "/order";
	public static final String PRODUCT_BASE = API_V1 + "/products";

	public static final String CATEGORIES_BASE = API_V1 + "/categories";
	public static final String REVIEWS_BASE = API_V1 + "/reviews";
	public static final String ADMIN_BASE = API_V1 + "/admin";
	public static final String PROFILE_BASE = API_V1 + "/profile";
	public static final String PASSWORD_BASE = API_V1 + "/password-reset";
	public static final String DISCOUNT_BASE = API_V1 + "/discount";
	public static final String ADMIN_CATEGORY = API_V1 + "/admin/categories";


	// ENDPOINT paths для методов
	public static final String REGISTER = "/register";
	public static final String LOGIN = "/login";
	public static final String CHECKOUT = "/checkout";
	public static final String ITEMS = "/items";
	public static final String CLEAR_CART = "/clear";
	public static final String SEARCH = "/search";
	public static final String ITEMS_QUANTITY = ITEMS + "/quantity";
	public static final String REMOVE_ITEM = ITEMS + "/{cartItemId}";  // ✅ ИСПРАВЬ ТУТ
	public static final String CREATE_PRODUCT = "/create";
	public static final String GET_MY_PRODUCTS = "/my-products";
	public static final String TOP_PRODUCTS = "/top";
	public static final String PRODUCTS_BY_CATEGORY = "/category";
	public static final String CURRENT_USER = "/me";
	public static final String UPDATE_PRODUCT = "/update";
	public static final String REVIEW_PRODUCT = "/product";
	public static final String REVIEW_HELPFUL = "/helpful";
	public static final String MY_REVIEWS = "/my-reviews";
	public static final String ROOT = "/root";
	public static final String SUBCATEGORIES = "/subcategories";
	public static final String BY_SLUG = "/slug";
	public static final String USER_STATUS  = "/status";
	public static final String USER_STATISTICS = "/statistics";
	public static final String PASSWORD_FORGOT = "/forgot";
	public static final String PASSWORD_RESET = "/reset";
	public static final String SUB = "/sub";



	// PATH VARIABLES (шаблоны с {})
	public static final String USER_ID = "/{userId}";
	public static final String PRODUCT_ID = "/{productId}";
	public static final String ORDER_ID = "/{orderId}";
	public static final String CATEGORY_NAME = "/{category}";
	public static final String ITEM_ID = "/{itemId}";
	public static final String REVIEW_ID = "/{reviewId}";
	public static final String PARENT_ID = "/{parentId}";
	public static final String CATEGORY_ID = "/{categoryId}";
	public static final String SLUG = "/{slug}";
}