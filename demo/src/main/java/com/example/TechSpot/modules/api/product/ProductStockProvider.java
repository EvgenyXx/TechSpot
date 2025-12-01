package com.example.TechSpot.modules.api.product;

public interface ProductStockProvider {

	boolean reduceQuantity(Long productId, int quantity);
}
