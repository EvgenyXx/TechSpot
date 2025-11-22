package com.example.TechSpot.service.product;


import com.example.TechSpot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductInventoryService {



	private final ProductRepository productRepository;

	public boolean reduceQuantity(Long productId, int quantity) {
		int updatedRows = productRepository.reduceQuantity(productId, quantity);
		return updatedRows > 0; // true если уменьшили, false если товара не хватило
	}
}
