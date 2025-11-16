package com.example.TechSpot.controller.product;


import com.example.TechSpot.dto.product.ProductCreateRequest;
import com.example.TechSpot.dto.product.ProductResponse;
import com.example.TechSpot.security.CustomUserDetail;
import com.example.TechSpot.service.product.ProductCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/command")
@RequiredArgsConstructor
@Log4j2
public class ProductCommandController {

	private final ProductCommandService productCommandService;

	@PostMapping("/create")
	public ResponseEntity<ProductResponse> createProduct(
			@Valid @RequestBody ProductCreateRequest request,
			@AuthenticationPrincipal CustomUserDetail customUserDetail){
		log.info(" HTTP POST api/product/create-product {}", request.productName());

		ProductResponse productResponse = productCommandService.createProduct(request,customUserDetail.id());
		log.info("HTTP 201 товар успешно создан {}", request.productName());
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(productResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(
			@PathVariable Long id,
			@RequestHeader("X-Customer-Id") UUID customerId) {

		log.info("DELETE /api/v1/products/{} - Удаление товара", id);
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

}
