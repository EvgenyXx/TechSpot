package com.example.techspot.modules.api.product;

public interface ProductStockProvider {

	void reduceQuantity(Long productId, int quantity);
}
