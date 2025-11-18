package com.example.TechSpot.service.product;

import com.example.TechSpot.entity.Product;
import com.example.TechSpot.exception.product.ProductNotFoundException;
import com.example.TechSpot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductFinder {


	private final ProductRepository productRepository;

	public Product findById(Long productId){
		return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
	}

}