package com.example.TechSpot.service.cart;

import com.example.TechSpot.dto.cart.response.CartResponse;
import com.example.TechSpot.dto.cart.request.AddToCartRequest;
import com.example.TechSpot.dto.cart.request.UpdateQuantityRequest;
import com.example.TechSpot.entity.Cart;
import com.example.TechSpot.entity.CartItems;
import com.example.TechSpot.entity.Product;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.exception.cart.CartItemsNotFoundException;
import com.example.TechSpot.mapping.CartMapper;
import com.example.TechSpot.repository.CartRepository;
import com.example.TechSpot.service.product.ProductFinder;
import com.example.TechSpot.service.user.query.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommandCartService {

	private final CartRepository cartRepository;
	private final UserFinder userFinder;
	private final CartMapper cartMapper;
	private final ProductFinder productFinder;
	private final CartItemService cartItemService;
	private final CartValidationService cartValidationService;
	private final CartCalculatorService cartCalculatorService;
	private final CartInitializationService cartInitializationService;



	@Transactional
	public CartResponse addToCart(AddToCartRequest request, UUID userId) {
		log.info("Начало добавления товара в корзину: productId={}, quantity={}, userId={}",
				request.productId(), request.quantity(), userId);

		User user = userFinder.findById(userId);
		Cart cart = cartInitializationService.ensureUserHasCart(user);
		Product product = productFinder.findById(request.productId());

		cartValidationService.validateProductQuantity(product, request.quantity(), cart);
		cartItemService.createOrUpdateCartItems(cart, product, request.quantity());
		cartCalculatorService.recalculateCartTotal(cart);

		Cart savedCart = cartRepository.save(cart);
		log.info("Товар добавлен в корзину. Итоговое количество позиций: {}, общая стоимость: {}",
				savedCart.getCartItems().size(), savedCart.getTotalPrice());
		return cartMapper.toCart(savedCart);
	}

	@Transactional
	public CartResponse removeFromCart(Long cartItemId, UUID userId) {
		log.info("Запрос на удаление товара из корзины: cartItemId={}, userId={}", cartItemId, userId);

		User user = userFinder.findById(userId);
		Cart cart = cartInitializationService.ensureUserHasCart(user);

		boolean removed = cart.getCartItems()
				.removeIf(cartItems -> cartItems.getId().equals(cartItemId));

		if (!removed) {
			log.warn("Позиция корзины не найдена для удаления: cartItemId={}, userId={}", cartItemId, userId);
			throw new CartItemsNotFoundException();
		}

		cartCalculatorService.recalculateCartTotal(cart);
		Cart savedCart = cartRepository.save(cart);
		log.info("Позиция удалена из корзины. Осталось позиций: {}", savedCart.getCartItems().size());
		return cartMapper.toCart(savedCart);
	}

	@Transactional
	public CartResponse updateQuantity(UpdateQuantityRequest request, UUID userId) {
		log.info("Изменение количества товара в корзине: cartItemId={}, newQuantity={}, userId={}",
				request.cartItemId(), request.newQuantity(), userId);

		User user = userFinder.findById(userId);
		Cart cart = cartInitializationService.ensureUserHasCart(user);

		CartItems cartItems = cart.getCartItems()
				.stream()
				.filter(item -> item.getId().equals(request.cartItemId()))
				.findFirst()
				.orElseThrow(() -> {
					log.warn("Позиция для обновления не найдена: cartItemId={}, userId={}",
							request.cartItemId(), userId);
					return new CartItemsNotFoundException();
				});

		cartItems.setQuantity(request.newQuantity());
		cartCalculatorService.recalculateCartTotal(cart);
		Cart savedCart = cartRepository.save(cart);
		log.info("Количество товара изменено. Актуальная сумма корзины: {}", savedCart.getTotalPrice());
		return cartMapper.toCart(savedCart);
	}

	@Transactional
	public void clearCart(UUID userId) {
		log.info("Запуск процедуры очистки корзины пользователя: {}", userId);
		User user = userFinder.findById(userId);
		Cart cart = cartInitializationService.ensureUserHasCart(user);

		int itemsCount = cart.getCartItems().size();
		cart.getCartItems().clear();
		cart.setTotalPrice(BigDecimal.ZERO);
		cartRepository.save(cart);
		log.info("Корзина пользователя {} полностью очищена. Удалено позиций: {}", userId, itemsCount);
	}














}