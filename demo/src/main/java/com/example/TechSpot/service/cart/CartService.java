package com.example.TechSpot.service.cart;

import com.example.TechSpot.dto.cart.response.CartResponse;
import com.example.TechSpot.dto.cart.request.AddToCartRequest;
import com.example.TechSpot.dto.cart.request.RemoveFromCartRequest;
import com.example.TechSpot.dto.cart.request.UpdateQuantityRequest;
import com.example.TechSpot.entity.Cart;
import com.example.TechSpot.entity.CartItems;
import com.example.TechSpot.entity.Product;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.exception.product.InsufficientStockException;
import com.example.TechSpot.exception.cart.CartItemsNotFoundException;
import com.example.TechSpot.exception.cart.CartNotFoundException;
import com.example.TechSpot.mapping.CartMapper;
import com.example.TechSpot.repository.CartRepository;
import com.example.TechSpot.service.product.ProductFinder;
import com.example.TechSpot.service.user.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Log4j2
public class CartService {

	private final CartRepository cartRepository;

	private final UserFinder userFinder;

	private final CartMapper cartMapper;

	private final ProductFinder productFinder;

	// 1. ✅ ПОЛУЧИТЬ КОРЗИНУ ПОЛЬЗОВАТЕЛЯ
	public CartResponse getCart(UUID userId) {

		Cart cart = cartRepository.findByUserId(userId)
				.orElseGet(() -> createNewCart(userId));

		return cartMapper.toCart(cart);
	}

	// Вспомогательный метод для создания новой корзины
	private Cart createNewCart(UUID userId) {
		User user = userFinder.findById(userId);

		Cart newCart = Cart.builder()
				.cartItems(new HashSet<>())
				.user(user)
				.totalPrice(BigDecimal.ZERO)
				.build();

		return cartRepository.save(newCart); // Сохраняем новую корзину в БД
	}

	public CartResponse addToCart(AddToCartRequest request,UUID userId) {

		Product product = productFinder.findById(request.productId());
		validateProductQuantity(product,request.quantity());
		Cart cart = cartRepository.findByUserId(userId)
				.orElseGet(() -> createNewCart(userId));



		createOrUpdateCartItems(cart, product, request.quantity());
		recalculateCartTotal(cart);

		Cart saveCart = cartRepository.save(cart);


		return cartMapper.toCart(saveCart);
	}

	private void recalculateCartTotal(Cart cart) {
		BigDecimal total = cart.getCartItems().stream()
				.map(item -> item.getItemPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		cart.setTotalPrice(total);
	}

	private void validateProductQuantity(Product product ,int quantity){
		if (product.getQuantity() < quantity || product.getQuantity() == 0){
			throw new InsufficientStockException();
		}
	}

	private void createOrUpdateCartItems(Cart cart, Product product, int quantity) {
		Optional<CartItems> cartItems = findExistingItem(cart, product.getId());

		if (cartItems.isPresent()) {
			CartItems items = cartItems.get();
			items.setQuantity(items.getQuantity() + quantity);

		} else {
			CartItems newCartItems = CartItems.builder()
					.cart(cart)
					.product(product)
					.quantity(quantity)
					.itemPrice(product.getPrice())
					.build();
			cart.getCartItems().add(newCartItems);
		}
	}

	private Optional<CartItems> findExistingItem(Cart cart, Long productId) {
		return cart.getCartItems().stream()
				.filter(cartItems -> cartItems.getProduct().getId().equals(productId))
				.findFirst();
	}

	public CartResponse removeFromCart(Long cartItemId,UUID userId) {

		Cart cartUser = checkingCartForUser(userId);
		boolean removed = cartUser.getCartItems()
						.removeIf(cartItems -> cartItems.getId().equals(cartItemId));

		if (!removed){
			throw new CartItemsNotFoundException();
		}
		recalculateCartTotal(cartUser);

		Cart saveCart  = cartRepository.save(cartUser);

		return cartMapper.toCart(saveCart);
	}

	// 4. ✅ ИЗМЕНИТЬ КОЛИЧЕСТВО ТОВАРА
	public CartResponse updateQuantity(UpdateQuantityRequest request,UUID userId) {

		Cart cart = checkingCartForUser(userId);

		CartItems cartItems = cart.getCartItems()
				.stream()
				.filter(cartItems1 -> cartItems1.getId().equals(request.cartItemId()))
				.findFirst()
				.orElseThrow(CartItemsNotFoundException::new);
		cartItems.setQuantity(request.newQuantity());
		recalculateCartTotal(cart);
		Cart saveCart = cartRepository.save(cart);

		return cartMapper.toCart(saveCart);
	}

	// 5. ✅ ОЧИСТИТЬ КОРЗИНУ
	public void clearCart(UUID userId) {
		Cart cart = checkingCartForUser(userId);
		cart.getCartItems().clear();
		cart.setTotalPrice(BigDecimal.ZERO);
		cartRepository.save(cart);
	}

	private Cart checkingCartForUser(UUID userId){
		return   cartRepository.findByUserId(userId)
				.orElseThrow(CartNotFoundException::new);
	}

	public Cart findByUserId(UUID userId){
		return cartRepository.findByUserId(userId)
				.orElseThrow(CartNotFoundException::new);
	}


}