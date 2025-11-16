package com.example.TechSpot.constants;

public class ApiPaths {

	private ApiPaths (){

	}

	public static final String API_V1 = "/api/v1";

	// BASE paths для контроллеров
	public static final String AUTH_BASE = API_V1 + "/auth";
	public static final String CART_BASE = API_V1 + "/cart";
	public static final String ORDER_BASE = API_V1 + "/order";
	public static final String COMMAND_BASE = API_V1 + "/command";
	public static final String QUERY_BASE = API_V1 + "/query";
	public static final String CATEGORIES_BASE = API_V1 + "/categories";

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

	// PATH VARIABLES (шаблоны с {})
	public static final String USER_ID = "/{userId}";
	public static final String PRODUCT_ID = "/{productId}";
	public static final String ORDER_ID = "/{orderId}";
	public static final String CATEGORY_NAME = "/{category}";
	public static final String ITEM_ID = "/{itemId}";
}