//package com.example.TechSpot.service;
//
//import com.example.TechSpot.dto.cart.response.CartItemResponse;
//import com.example.TechSpot.dto.cart.response.CartResponse;
//import com.example.TechSpot.dto.cart.request.AddToCartRequest;
//import com.example.TechSpot.dto.cart.request.RemoveFromCartRequest;
//import com.example.TechSpot.dto.cart.request.UpdateQuantityRequest;
//import com.example.TechSpot.modules.cart.entity.Cart;
//import com.example.TechSpot.modules.cart.entity.CartItems;
//import com.example.TechSpot.modules.products.entity.Product;
//import com.example.TechSpot.modules.auth.users.User;
//
//import com.example.TechSpot.mapping.CartMapper;
//import com.example.TechSpot.modules.cart.repository.CartRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//class CartServiceTest {
//
//	@Mock private CartRepository cartRepository;
//	@Mock private UserService userService;
//	@Mock private CartMapper cartMapper;
//	@Mock private ProductService productService;
//	@InjectMocks private CartService cartService;
//
//	// === TEST DATA BUILDERS ===
//
//	private User createUser(UUID userId) {
//		return User.builder().id(userId).firstname("evgeny").build();
//	}
//
//
//	private Product createProduct() {
//		return Product.builder().id(1L).productName("phone").price(BigDecimal.valueOf(999.00)).build();
//	}
//
//	private Cart createCart(User user, boolean withItems) {
//		Cart cart = Cart.builder()
//				.id(1L).user(user).cartItems(new HashSet<>()).totalPrice(BigDecimal.ZERO)
//				.build();
//
//		if (withItems) {
//			CartItems item = CartItems.builder()
//					.id(1L).product(createProduct()).quantity(2)
//					.itemPrice(BigDecimal.valueOf(999.00)).cart(cart)
//					.build();
//			cart.getCartItems().add(item);
//			cart.setTotalPrice(BigDecimal.valueOf(1998.00)); // 999 × 2
//		}
//		return cart;
//	}
//
//
//
//	// 🔥 ИСПРАВИЛ - создал ОТДЕЛЬНЫЙ метод для теста добавления
//	private CartResponse createCartResponseForAddTest() {
//		CartItemResponse itemResponse = new CartItemResponse("phone");
//		return new CartResponse(
//				Set.of(itemResponse),
//				new BigDecimal("2997.00") // ← Используй строку или установи scale
//		);
//	}
//
//	private CartResponse createCartResponseForQuantity(int quantity) {
//		CartItemResponse itemResponse = new CartItemResponse("phone");
//		return new CartResponse(
//				Set.of(itemResponse),
//				new BigDecimal("999.00").multiply(BigDecimal.valueOf(quantity))
//		);
//	}
//
//	private Cart createCartWithItem(User user, Product product, int quantity) {
//		Cart cart = Cart.builder()
//				.id(1L)
//				.user(user)
//				.cartItems(new HashSet<>())
//				.totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity))) // 999 × 3 = 2997
//				.build();
//
//		CartItems cartItem = CartItems.builder()
//				.id(1L)
//				.product(product)
//				.quantity(quantity) // 3
//				.itemPrice(product.getPrice())
//				.cart(cart)
//				.build();
//
//		cart.getCartItems().add(cartItem);
//		return cart;
//	}
//	private Cart createCartWithTwoItems(User user) {
//		Product product = createProduct();
//		Cart cart = Cart.builder()
//				.id(1L)
//				.user(user)
//				.cartItems(new HashSet<>())
//				.totalPrice(new BigDecimal("2997.00")) // 999×3
//				.build();
//
//		// Первый товар
//		CartItems item1 = CartItems.builder()
//				.id(1L)
//				.product(product)
//				.quantity(2)
//				.itemPrice(product.getPrice())
//				.cart(cart)
//				.build();
//
//		// Второй товар
//		CartItems item2 = CartItems.builder()
//				.id(2L)
//				.product(createProduct())
//				.quantity(1)
//				.itemPrice(new BigDecimal("999.00"))
//				.cart(cart)
//				.build();
//
//		cart.getCartItems().add(item1);
//		cart.getCartItems().add(item2);
//		return cart;
//	}
//
//
//	// === ТЕСТЫ ===
//
//	@Test
//	void getCart_ShouldReturnCart_WhenCartExists() {
//		// given
//		UUID userId = UUID.randomUUID();
//		Cart cart = createCart(createUser(userId), true);
//		CartResponse expected = createCartResponseForAddTest(); // 🔥 используем правильный метод
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
//		when(cartMapper.toCart(cart)).thenReturn(expected);
//
//		// when
//		CartResponse result = cartService.getCart(userId);
//
//		// then
//		assertEquals(expected, result);
//	}
//
//	@Test
//	void getCart_ShouldCreateNewCart_WhenCartNotExists() {
//		// given
//		UUID userId = UUID.randomUUID();
//		User user = createUser(userId);
//		Cart newCart = createCart(user, false);
//		CartResponse expected = createCartResponseForAddTest(); // 🔥 используем правильный метод
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
//		when(userService.findById(userId)).thenReturn(user);
//		when(cartRepository.save(any(Cart.class))).thenReturn(newCart);
//		when(cartMapper.toCart(newCart)).thenReturn(expected);
//
//		// when
//		CartResponse result = cartService.getCart(userId);
//
//		// then
//		assertEquals(expected, result);
//		verify(cartRepository).save(any(Cart.class));
//	}
//
//	@Test
//	void addToCart_ShowSuccess_WhenValid(){
//		UUID userId = UUID.randomUUID();
//
//		User user = createUser(userId);
//		Cart cartEmpty = createCart(user, false);
//		Product product = createProduct();
//		Cart cartWithItems = createCartWithItem(user, product, 3);
//
//		// 🔥 ИСПРАВИЛ - используем правильный expected response
//		CartResponse expectedResponse = createCartResponseForAddTest();
//		AddToCartRequest request = new AddToCartRequest(
//				userId,product.getId(),3
//		);
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cartEmpty));
//		when(productService.findById(product.getId())).thenReturn(product);
//		when(cartRepository.save(any(Cart.class))).thenReturn(cartWithItems);
//		when(cartMapper.toCart(cartWithItems)).thenReturn(expectedResponse); // 🔥 передаем правильный expected
//
//		CartResponse result = cartService.addToCart(request);
//
//		// 🔥 Теперь можно проверять весь объект
//		assertEquals(expectedResponse, result);
//
//		// Или конкретные поля для надежности:
//		assertNotNull(result);
//		assertEquals(1, result.cartItemResponses().size());
//		assertEquals("phone", result.cartItemResponses().iterator().next().productName());
//		assertEquals(new BigDecimal("2997.00"), result.totalPrice());
//	}
//	@Test
//	void addToCart_ShouldAddNewItem_WhenCartEmpty() {
//		// given - пустая корзина
//		UUID userId = UUID.randomUUID();
//		User user = createUser(userId);
//		Cart emptyCart = createCart(user, false);
//		Product product = createProduct();
//
//		Cart cartWithItem = createCartWithItem(user, product, 3);
//		CartResponse expectedResponse = createCartResponseForQuantity(3);
//		AddToCartRequest request = new AddToCartRequest(
//				userId,product.getId(),3
//		);
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(emptyCart));
//		when(productService.findById(product.getId())).thenReturn(product);
//		when(cartRepository.save(any(Cart.class))).thenReturn(cartWithItem);
//		when(cartMapper.toCart(cartWithItem)).thenReturn(expectedResponse);
//
//		// when
//		CartResponse result = cartService.addToCart(request);
//
//		// then
//		assertEquals(expectedResponse, result);
//	}
//
//	@Test
//	void removeFromCart_ShouldRemoveItem_WhenItemExists() {
//		// given - корзина с товаром
//		UUID userId = UUID.randomUUID();
//		User user = createUser(userId);
//		Product product = createProduct();
//
//		// Создаем корзину с товаром
//		Cart cartWithItem = createCartWithItem(user, product, 2);
//		Long cartItemId = 1L;
//
//		// Корзина после удаления (пустая)
//		Cart emptyCart = createCart(user, false);
//		CartResponse expectedResponse = createCartResponse(false); // ← используем существующий метод
//		RemoveFromCartRequest request = new RemoveFromCartRequest(
//				userId,cartItemId
//		);
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cartWithItem));
//		when(cartRepository.save(any(Cart.class))).thenReturn(emptyCart);
//		when(cartMapper.toCart(emptyCart)).thenReturn(expectedResponse);
//
//		// when
//		CartResponse result = cartService.removeFromCart(request);
//
//		// then
//		assertEquals(expectedResponse, result);
//	}
//
//	@Test
//	void removeFromCart_ShouldThrow_WhenCartNotFound() {
//		// given - корзины нет
//		UUID userId = UUID.randomUUID();
//		Long cartItemId = 1L;
//
//		RemoveFromCartRequest request = new RemoveFromCartRequest(
//				userId,cartItemId
//		);
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
//
//
//		// when & then
//		assertThrows(RuntimeException.class, () -> {
//			cartService.removeFromCart(request);
//		});
//	}
//
//	@Test
//	void removeFromCart_ShouldThrow_WhenCartItemNotFound() {
//		// given - корзина есть, но такого cartItemId нет
//		UUID userId = UUID.randomUUID();
//		User user = createUser(userId);
//		Cart cart = createCart(user, true);
//		Long nonExistingCartItemId = 999L;
//
//		RemoveFromCartRequest request = new RemoveFromCartRequest(
//				userId,nonExistingCartItemId
//		);
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
//
//		// when & then
//		assertThrows(RuntimeException.class, () -> {
//			cartService.removeFromCart(request);
//		});
//	}
//
//	@Test
//	void removeFromCart_ShouldRecalculateTotal_AfterRemoval() {
//		// given - корзина с товаром
//		UUID userId = UUID.randomUUID();
//		User user = createUser(userId);
//		Product product = createProduct();
//
//		// Корзина с товаром (quantity = 2)
//		Cart cartWithItem = createCartWithItem(user, product, 2);
//		Long cartItemId = 1L;
//
//		// После удаления - пустая корзина
//		Cart emptyCart = createCart(user, false);
//		CartResponse expectedResponse = createCartResponse(false); // ← пустой ответ
//		RemoveFromCartRequest request = new RemoveFromCartRequest(
//				userId,cartItemId
//		);
//
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cartWithItem));
//		when(cartRepository.save(any(Cart.class))).thenReturn(emptyCart);
//		when(cartMapper.toCart(emptyCart)).thenReturn(expectedResponse);
//
//		// when
//		CartResponse result = cartService.removeFromCart(request);
//
//		// then
//		assertEquals(expectedResponse, result);
//		assertEquals(BigDecimal.ZERO, result.totalPrice()); // проверяем что сумма обнулилась
//	}
//	private CartResponse createCartResponse(boolean withItems) {
//		CartItemResponse itemResponse = new CartItemResponse("phone");
//		return new CartResponse(
//				withItems ? Set.of(itemResponse) : Set.of(),
//				withItems ? BigDecimal.valueOf(1998.00) : BigDecimal.ZERO
//		);
//	}
//
//
//	@Test
//	void updateQuantity_ShouldUpdateQuantity_WhenValid() {
//		// given - корзина с товаром (quantity = 2)
//		UUID userId = UUID.randomUUID();
//		User user = createUser(userId);
//		Cart cart = createCartWithItem(user, createProduct(), 2);
//		Long cartItemId = 1L;
//		int newQuantity = 5;
//
//		// Ожидаем корзину с quantity = 5
//		Cart updatedCart = createCartWithItem(user, createProduct(), 5);
//		CartResponse expectedResponse = createCartResponseForQuantity(5);
//		UpdateQuantityRequest request = new UpdateQuantityRequest(
//				userId,cartItemId,newQuantity
//		);
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
//		when(cartRepository.save(any(Cart.class))).thenReturn(updatedCart);
//		when(cartMapper.toCart(updatedCart)).thenReturn(expectedResponse);
//
//		// when
//		CartResponse result = cartService.updateQuantity(request);
//
//		// then
//		assertEquals(expectedResponse, result);
//	}
//
//	@Test
//	void updateQuantity_ShouldThrow_WhenCartNotFound() {
//		// given - корзины нет
//		UUID userId = UUID.randomUUID();
//		Long cartItemId = 1L;
//		int newQuantity = 5;
//
//		UpdateQuantityRequest request = new UpdateQuantityRequest(
//				userId,cartItemId,newQuantity
//		);
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
//
//		// when & then
//		assertThrows(RuntimeException.class, () -> {
//			cartService.updateQuantity(request);
//		});
//	}
//
//	@Test
//	void updateQuantity_ShouldThrow_WhenCartItemNotFound() {
//		// given - корзина есть, но cartItemId не существует
//		UUID userId = UUID.randomUUID();
//		User user = createUser(userId);
//		Cart cart = createCart(user, true);
//		Long nonExistingCartItemId = 999L;
//		int newQuantity = 5;
//
//		UpdateQuantityRequest request = new UpdateQuantityRequest(
//				userId,nonExistingCartItemId,newQuantity
//		);
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
//
//		// when & then
//		assertThrows(RuntimeException.class, () -> {
//			cartService.updateQuantity(request);
//		});
//	}
//
//	@Test
//	void updateQuantity_ShouldRecalculateTotal_AfterUpdate() {
//		// given - корзина с товаром (quantity = 2, сумма = 1998)
//		UUID userId = UUID.randomUUID();
//		User user = createUser(userId);
//		Cart cart = createCartWithItem(user, createProduct(), 2);
//		Long cartItemId = 1L;
//		int newQuantity = 3; // новая сумма должна быть 2997
//
//		// Ожидаем корзину с quantity = 3
//		Cart updatedCart = createCartWithItem(user, createProduct(), 3);
//		CartResponse expectedResponse = createCartResponseForQuantity(3);
//
//		UpdateQuantityRequest request = new UpdateQuantityRequest(
//				userId,cartItemId,newQuantity
//		);
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
//		when(cartRepository.save(any(Cart.class))).thenReturn(updatedCart);
//		when(cartMapper.toCart(updatedCart)).thenReturn(expectedResponse);
//
//		// when
//		CartResponse result = cartService.updateQuantity(request);
//
//		// then
//		assertEquals(expectedResponse, result);
//		assertEquals(new BigDecimal("2997.00"), result.totalPrice()); // 999 × 3
//	}
//
//	@Test
//	void clearCart_ShouldClearAllItemsAndResetTotal_WhenCartExists() {
//		// given - корзина с товарами
//		UUID userId = UUID.randomUUID();
//		User user = createUser(userId);
//		Cart cartWithItems = createCartWithItem(user, createProduct(), 2);
//
//		// Корзина после очистки
//		Cart clearedCart = createCart(user, false);
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cartWithItems));
//		when(cartRepository.save(any(Cart.class))).thenReturn(clearedCart);
//
//		// when
//		cartService.clearCart(userId);
//
//		// then
//		verify(cartRepository).findByUserId(userId);
//		verify(cartRepository).save(cartWithItems);
//
//		// Проверяем, что корзина очищена
//		assertTrue(cartWithItems.getCartItems().isEmpty());
//		assertEquals(BigDecimal.ZERO, cartWithItems.getTotalPrice());
//	}
//
//	@Test
//	void clearCart_ShouldThrowException_WhenCartNotFound() {
//		// given - корзина не существует
//		UUID userId = UUID.randomUUID();
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
//
//		// when & then
//		assertThrows(RuntimeException.class, () -> {
//			cartService.clearCart(userId);
//		});
//
//		verify(cartRepository).findByUserId(userId);
//		verify(cartRepository, never()).save(any(Cart.class));
//	}
//
//	@Test
//	void clearCart_ShouldWork_WhenCartAlreadyEmpty() {
//		// given - пустая корзина
//		UUID userId = UUID.randomUUID();
//		User user = createUser(userId);
//		Cart emptyCart = createCart(user, false);
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(emptyCart));
//		when(cartRepository.save(any(Cart.class))).thenReturn(emptyCart);
//
//		// when
//		cartService.clearCart(userId);
//
//		// then
//		verify(cartRepository).findByUserId(userId);
//		verify(cartRepository).save(emptyCart);
//
//		// Проверяем, что корзина осталась пустой
//		assertTrue(emptyCart.getCartItems().isEmpty());
//		assertEquals(BigDecimal.ZERO, emptyCart.getTotalPrice());
//	}
//
//	@Test
//	void clearCart_ShouldClearMultipleItems_WhenCartHasMultipleItems() {
//		// given - корзина с несколькими товарами
//		UUID userId = UUID.randomUUID();
//		User user = createUser(userId);
//		Cart cartWithMultipleItems = createCartWithTwoItems(user);
//
//		// Проверяем, что изначально есть товары
//		assertEquals(2, cartWithMultipleItems.getCartItems().size());
//		assertTrue(cartWithMultipleItems.getTotalPrice().compareTo(BigDecimal.ZERO) > 0);
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cartWithMultipleItems));
//		when(cartRepository.save(any(Cart.class))).thenReturn(cartWithMultipleItems);
//
//		// when
//		cartService.clearCart(userId);
//
//		// then
//		verify(cartRepository).save(cartWithMultipleItems);
//
//		// Проверяем, что все товары удалены
//		assertTrue(cartWithMultipleItems.getCartItems().isEmpty());
//		assertEquals(BigDecimal.ZERO, cartWithMultipleItems.getTotalPrice());
//	}
//
//	@Test
//	void clearCart_ShouldPersistChangesToDatabase() {
//		// given
//		UUID userId = UUID.randomUUID();
//		User user = createUser(userId);
//		Cart cartWithItems = createCartWithItem(user, createProduct(), 1);
//
//		when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cartWithItems));
//		when(cartRepository.save(cartWithItems)).thenReturn(cartWithItems);
//
//		// when
//		cartService.clearCart(userId);
//
//		// then
//		// Проверяем, что save вызывается с правильной корзиной
//		verify(cartRepository).save(cartWithItems);
//
//		// Проверяем состояние корзины после очистки
//		ArgumentCaptor<Cart> cartCaptor = ArgumentCaptor.forClass(Cart.class);
//		verify(cartRepository).save(cartCaptor.capture());
//
//		Cart savedCart = cartCaptor.getValue();
//		assertTrue(savedCart.getCartItems().isEmpty());
//		assertEquals(BigDecimal.ZERO, savedCart.getTotalPrice());
//	}
//}