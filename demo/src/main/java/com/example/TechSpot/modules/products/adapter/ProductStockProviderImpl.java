package com.example.TechSpot.modules.products.adapter;

import com.example.TechSpot.modules.api.product.ProductStockProvider;
import com.example.TechSpot.modules.products.service.command.ProductStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Log4j2
@Service
public class ProductStockProviderImpl implements ProductStockProvider {

	private final ProductStockService productStockService;

	@Override
	public boolean reduceQuantity(Long productId, int quantity) {
		return productStockService.reduceQuantity(productId, quantity);
	}
}
