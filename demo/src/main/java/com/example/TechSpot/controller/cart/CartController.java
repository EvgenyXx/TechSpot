package com.example.TechSpot.controller.cart;


import com.example.TechSpot.dto.cart.request.AddToCartRequest;
import com.example.TechSpot.dto.cart.request.RemoveFromCartRequest;
import com.example.TechSpot.dto.cart.request.UpdateQuantityRequest;
import com.example.TechSpot.dto.cart.response.CartResponse;
import com.example.TechSpot.security.CustomUserDetail;
import com.example.TechSpot.service.cart.CartService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@GetMapping("/{userId}")
	public ResponseEntity<CartResponse> getCart(@PathVariable UUID userId) {
		return ResponseEntity.ok(cartService.getCart(userId));
	}

	@PostMapping("/items")
	public ResponseEntity<CartResponse> addItem(@RequestBody AddToCartRequest request,
												@AuthenticationPrincipal CustomUserDetail userDetail) {
		return ResponseEntity.ok(cartService.addToCart(request,userDetail.id()));
	}

	@PutMapping("/items/quantity")
	public ResponseEntity<CartResponse> updateQuantity(@RequestBody UpdateQuantityRequest request) {
		return ResponseEntity.ok(cartService.updateQuantity(request));
	}

	@DeleteMapping("/items")
	public ResponseEntity<CartResponse> removeItem(@RequestBody RemoveFromCartRequest request) {
		return ResponseEntity.ok(cartService.removeFromCart(request));
	}

	@DeleteMapping("/{userId}/clear")
	public ResponseEntity<Void> clearCart(@PathVariable UUID userId) {
		cartService.clearCart(userId);
		return ResponseEntity.noContent().build(); // 204 вместо 200
	}
}
