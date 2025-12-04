package com.example.TechSpot.modules.discount;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

	private final DiscountCreateService discountCreateService;

	@PostMapping
	public void create(@RequestBody CreateDiscountRequest request) {
		discountCreateService.create(request);
	}
}