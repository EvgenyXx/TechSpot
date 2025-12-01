package com.example.TechSpot.modules.products.service.command;


import com.example.TechSpot.modules.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductStockService {



	private final ProductRepository productRepository;

	public boolean reduceQuantity(Long productId, int quantity) {
		int updatedRows = productRepository.reduceQuantity(productId, quantity);
		return updatedRows > 0; // true если уменьшили, false если товара не хватило
	}
}
