package com.example.TechSpot.controller.order;


import com.example.TechSpot.constants.ApiPaths;
import com.example.TechSpot.dto.order.OrderResponse;
import com.example.TechSpot.security.CustomUserDetail;
import com.example.TechSpot.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.ORDER_BASE)
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;


	@PostMapping(ApiPaths.CHECKOUT)
	public ResponseEntity<OrderResponse>checkout(@AuthenticationPrincipal CustomUserDetail user){

		OrderResponse orderResponse = orderService.checkout(user.id());
		return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
	}
}
