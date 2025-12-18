package com.example.techspot.modules.discount.api;


import com.example.techspot.common.constants.ApiPaths;
import com.example.techspot.modules.discount.application.dto.CreateDiscountRequest;
import com.example.techspot.modules.discount.application.comand.DiscountCreateService;
import com.example.techspot.modules.discount.application.dto.DiscountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.DISCOUNT_BASE)
@RequiredArgsConstructor
public class DiscountController {

	private final DiscountCreateService discountCreateService;

	@PostMapping
	public ResponseEntity<DiscountResponse> create(@RequestBody @Valid CreateDiscountRequest request) {

	   DiscountResponse discountResponse =	discountCreateService.create(request);

	   return ResponseEntity.ok(discountResponse);
	}
}