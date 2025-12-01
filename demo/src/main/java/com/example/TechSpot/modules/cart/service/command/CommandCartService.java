package com.example.TechSpot.modules.cart.service.command;

import com.example.TechSpot.modules.api.product.ProductRepositoryProvider;
import com.example.TechSpot.modules.api.user.UserRepositoryProvider;
import com.example.TechSpot.modules.cart.dto.response.CartResponse;
import com.example.TechSpot.modules.cart.dto.request.AddToCartRequest;
import com.example.TechSpot.modules.cart.dto.request.UpdateQuantityRequest;
import com.example.TechSpot.modules.cart.entity.Cart;
import com.example.TechSpot.modules.cart.entity.CartItems;
import com.example.TechSpot.modules.cart.service.util.CartCalculator;
import com.example.TechSpot.modules.cart.service.util.CartItemManager;
import com.example.TechSpot.modules.cart.service.util.CartValidation;
import com.example.TechSpot.modules.products.entity.Product;
import com.example.TechSpot.modules.users.entity.User;
import com.example.TechSpot.modules.cart.exception.CartItemsNotFoundException;
import com.example.TechSpot.modules.cart.mapper.CartMapper;
import com.example.TechSpot.modules.cart.repository.CartRepository;
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
	private final UserRepositoryProvider userRepositoryProvider;
	private final CartMapper cartMapper;
	private final ProductRepositoryProvider productRepositoryProvider;
	private final CartItemManager cartItemManager;
	private final CartValidation cartValidation;
	private final CartCalculator cartCalculator;
	private final CartInitializationService cartInitializationService;



	@Transactional
	public CartResponse addToCart(AddToCartRequest request, UUID userId) {
		log.info("Начало добавления товара в корзину: productId={}, quantity={}, userId={}",
				request.productId(), request.quantity(), userId);

		User user = userRepositoryProvider.findById(userId);
		Cart cart = cartInitializationService.ensureUserHasCart(user);
		Product product = productRepositoryProvider.findById(request.productId());

		cartValidation.validateProductQuantity(product, request.quantity(), cart);
		cartItemManager.createOrUpdateCartItems(cart, product, request.quantity());
		cartCalculator.recalculateCartTotal(cart);

		Cart savedCart = cartRepository.save(cart);
		log.info("Товар добавлен в корзину. Итоговое количество позиций: {}, общая стоимость: {}",
				savedCart.getCartItems().size(), savedCart.getTotalPrice());
		return cartMapper.toCart(savedCart);
	}

	@Transactional
	public CartResponse removeFromCart(Long cartItemId, UUID userId) {
		log.info("Запрос на удаление товара из корзины: cartItemId={}, userId={}", cartItemId, userId);

		User user = userRepositoryProvider.findById(userId);
		Cart cart = cartInitializationService.ensureUserHasCart(user);

		boolean removed = cart.getCartItems()
				.removeIf(cartItems -> cartItems.getId().equals(cartItemId));

		if (!removed) {
			log.warn("Позиция корзины не найдена для удаления: cartItemId={}, userId={}", cartItemId, userId);
			throw new CartItemsNotFoundException();
		}

		cartCalculator.recalculateCartTotal(cart);
		Cart savedCart = cartRepository.save(cart);
		log.info("Позиция удалена из корзины. Осталось позиций: {}", savedCart.getCartItems().size());
		return cartMapper.toCart(savedCart);
	}

	@Transactional
	public CartResponse updateQuantity(UpdateQuantityRequest request, UUID userId) {
		log.info("Изменение количества товара в корзине: cartItemId={}, newQuantity={}, userId={}",
				request.cartItemId(), request.newQuantity(), userId);

		User user = userRepositoryProvider.findById(userId);
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
		cartCalculator.recalculateCartTotal(cart);
		Cart savedCart = cartRepository.save(cart);
		log.info("Количество товара изменено. Актуальная сумма корзины: {}", savedCart.getTotalPrice());
		return cartMapper.toCart(savedCart);
	}

	@Transactional
	public void clearCart(UUID userId) {
		log.info("Запуск процедуры очистки корзины пользователя: {}", userId);
		User user = userRepositoryProvider.findById(userId);
		Cart cart = cartInitializationService.ensureUserHasCart(user);

		int itemsCount = cart.getCartItems().size();
		cart.getCartItems().clear();
		cart.setTotalPrice(BigDecimal.ZERO);
		cartRepository.save(cart);
		log.info("Корзина пользователя {} полностью очищена. Удалено позиций: {}", userId, itemsCount);
	}














}