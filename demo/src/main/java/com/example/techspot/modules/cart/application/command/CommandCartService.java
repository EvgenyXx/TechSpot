package com.example.techspot.modules.cart.application.command;


import com.example.techspot.modules.cart.application.dto.response.CartResponse;
import com.example.techspot.modules.cart.application.dto.request.AddToCartRequest;
import com.example.techspot.modules.cart.application.dto.request.UpdateQuantityRequest;
import com.example.techspot.modules.cart.domain.entity.Cart;
import com.example.techspot.modules.cart.application.factory.CartResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CommandCartService {

	private final CartAccessService cartAccess;
	private final CartOperationService cartOps;
	private final CartPricingService pricing;
	private final CartResponseBuilder responseBuilder;

	@Transactional
	public CartResponse addToCart(AddToCartRequest request, UUID userId) {

		Cart cart = cartAccess.getCartForUser(userId);

		cartOps.add(cart, request.productId(), request.quantity());

		pricing.recalculate(cart);

		return responseBuilder.build(cart);
	}

	@Transactional
	public CartResponse removeFromCart(Long cartItemId, UUID userId) {

		Cart cart = cartAccess.getCartForUser(userId);

		cartOps.remove(cart, cartItemId);

		pricing.recalculate(cart);

		return responseBuilder.build(cart);
	}

	@Transactional
	public CartResponse updateQuantity(UpdateQuantityRequest request, UUID userId) {

		Cart cart = cartAccess.getCartForUser(userId);

		cartOps.updateQuantity(cart, request.cartItemId(), request.newQuantity());

		pricing.recalculate(cart);

		return responseBuilder.build(cart);
	}

	@Transactional
	public void clearCart(UUID userId) {

		Cart cart = cartAccess.getCartForUser(userId);

		cartOps.clear(cart);
	}
}
