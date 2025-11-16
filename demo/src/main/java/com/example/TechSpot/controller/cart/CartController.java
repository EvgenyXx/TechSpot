package com.example.TechSpot.controller.cart;


import com.example.TechSpot.constants.ApiPaths;
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

@RestController
@RequestMapping(ApiPaths.CART_BASE)
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@GetMapping
	public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
		return ResponseEntity.ok(cartService.getCart(customUserDetail.id()));
	}

	@PostMapping(ApiPaths.ITEMS)
	public ResponseEntity<CartResponse> addItem(@RequestBody AddToCartRequest request,
												@AuthenticationPrincipal CustomUserDetail userDetail) {
		return ResponseEntity.ok(cartService.addToCart(request,userDetail.id()));
	}

	@PutMapping(ApiPaths.ITEMS_QUANTITY)
	public ResponseEntity<CartResponse> updateQuantity(@RequestBody UpdateQuantityRequest request) {
		return ResponseEntity.ok(cartService.updateQuantity(request));
	}

	@DeleteMapping(ApiPaths.REMOVE_ITEM)
	public ResponseEntity<CartResponse> removeItem(@RequestBody RemoveFromCartRequest request) {
		return ResponseEntity.ok(cartService.removeFromCart(request));
	}

	@DeleteMapping(ApiPaths.CLEAR_CART)
	public ResponseEntity<Void> clearCart(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
		cartService.clearCart(customUserDetail.id());
		return ResponseEntity.noContent().build(); // 204 вместо 200
	}
}
