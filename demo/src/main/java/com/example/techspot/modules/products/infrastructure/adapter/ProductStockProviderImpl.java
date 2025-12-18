package com.example.techspot.modules.products.infrastructure.adapter;

import com.example.techspot.modules.api.product.ProductStockProvider;
import com.example.techspot.modules.products.domain.service.command.ProductStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Log4j2
@Service
public class ProductStockProviderImpl implements ProductStockProvider {

	private final ProductStockService productStockService;

	@Override
	public void reduceQuantity(Long productId, int quantity) {
		 productStockService.reduceQuantity(productId, quantity);
	}
}
