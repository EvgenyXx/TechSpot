//package com.example.TechSpot.service;
//
//import com.example.TechSpot.modules.products.dto.request.ProductCreateRequest;
//import com.example.TechSpot.modules.products.dto.response.ProductResponse;
//import com.example.TechSpot.modules.products.entity.Product;
//import com.example.TechSpot.modules.products.entity.ProductCategory;
//import com.example.TechSpot.modules.auth.users.User;
//import com.example.TechSpot.exception.product.ProductNotFoundException;
//import com.example.TechSpot.mapping.ProductMapper;
//import com.example.TechSpot.modules.products.repository.ProductRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.matches;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class ProductServiceTest {
//
//	@Mock
//	private ProductRepository productRepository;
//
//	@Mock
//	private ProductMapper productMapper;
//
//	@Mock
//	private UserService userService;
//
//	@InjectMocks
//	private ProductService productService;
//
//
//	private ProductCreateRequest createValidProduct(){
//		return new ProductCreateRequest(
//				"iphone",
//				new BigDecimal("999.00"),
//				3,
//				"айфон 16 про макс",
//				ProductCategory.ELECTRONICS,
//				UUID.fromString("b9124e8c-eaff-4832-9fe8-1928cf3acadb")
//				);
//	}
//
//	private ProductCreateRequest createInvalidRequest(){
//		return new ProductCreateRequest(
//				" ",
//				new BigDecimal("999.00"),
//				3,
//				"айфон 16 про макс",
//				ProductCategory.ELECTRONICS,
//				UUID.fromString("b9124e8c-eaff-4832-9fe8-1928cf3acadb")
//		);
//	}
//
//	private User createMockUser() {
//		User user = new User();
//		user.setId(UUID.fromString("b9124e8c-eaff-4832-9fe8-1928cf3acadb"));
//		user.setFirstname("Иван");
//		user.setLastname("Иванов");
//		user.setEmail("test@mail.com");
//		return user;
//	}
//
//	private Product createMockProduct() {
//		return Product.builder()
//				.id(1L)
//				.productName("iPhone")
//				.price(new BigDecimal("999.00"))
//				.quantity(3)
//				.description("Айфон 16 про макс")
//				.category(ProductCategory.ELECTRONICS)
//				.user(createMockUser())
//				.build();
//	}
//
//	private ProductResponse createMockProductResponse(){
//		return new ProductResponse(
//				1L,                                    // id
//				"iPhone",                              // productName
//				new BigDecimal("999.00"),              // price
//				3,                                     // quantity
//				"Айфон 16 про макс",                   // description
//				ProductCategory.ELECTRONICS,           // productCategory
//				"Иван",                                // sellerName
//				null,                                  // createdAt (можно оставить null)
//				null                                   // updatedAt (можно оставить null)
//		);
//	}
//
//	@Test
//	void createProduct_ShouldSuccess_WhenValidRequest() {
//		ProductCreateRequest productCreateRequest = createValidProduct();
//		User user = createMockUser();
//		UUID userId = UUID.fromString("b9124e8c-eaff-4832-9fe8-1928cf3acadb");
//
//		Product mockProduct = createMockProduct();
//
//		Product savaProduct = createMockProduct();
//
//		ProductResponse productResponse = createMockProductResponse();
//
//
//		when(productMapper.toProduct(productCreateRequest)).thenReturn(mockProduct);
//
//		when(userService.findById(userId)).thenReturn(user);
//
//		when(productRepository.save(mockProduct)).thenReturn(savaProduct);
//
//		when(productMapper.toResponseProduct(savaProduct)).thenReturn(productResponse);
//
//		ProductResponse result = productService.createProduct(productCreateRequest);
//
//		assertNotNull(result);
//
//		// 1. Проверяем что результат корректен
//		assertEquals(1L, result.id());
//		assertEquals("iPhone", result.productName());
//		assertEquals(new BigDecimal("999.00"), result.price());
//		assertEquals(3, result.quantity());
//		assertEquals("Айфон 16 про макс", result.description());
//		assertEquals(ProductCategory.ELECTRONICS, result.productCategory());
//		assertEquals("Иван", result.sellerName());
//
//
//	}
//
//	@Test
//	void createProduct_ShouldThrow_WhenMapperReturnsNull() {
//		// given
//		ProductCreateRequest request = createValidProduct();
//		UUID userId = UUID.fromString("b9124e8c-eaff-4832-9fe8-1928cf3acadb");
//		User user = userService.findById(userId);
//
//		when(productMapper.toProduct(request)).thenReturn(null);
//		when(userService.findById(userId)).thenReturn(user);
//
//		// when & then
//		assertThrows(NullPointerException.class, () -> {
//			productService.createProduct(request);
//		});
//	}
//
//	@Test
//	void getProductById_ShowSuccess_WhenProductExists(){
//		Long id = 1L;
//		User user = User.builder()
//				.lastname("ivanov")
//				.firstname("ivan")
//				.build();
//		Product product = Product.builder()
//				.id(id)
//				.productName("phone")
//				.price(new BigDecimal("999.99"))
//				.quantity(5)
//				.description("Смартфон")
//				.category(ProductCategory.ELECTRONICS)
//				.user(user)
//				.build();
//
//		ProductResponse expectedResponse = new ProductResponse(
//				id,
//				"phone",
//				new BigDecimal("999.99"),
//				5,
//				"Смартфон",
//				ProductCategory.ELECTRONICS,
//				"Иван",  // user.getFirstname()
//				null,    // createdAt - можно оставить null если не важно
//				null     // updatedAt - можно оставить null если не важно
//		);
//
//
//		when(productRepository.findById(id)).thenReturn(Optional.of(product));
//
//		when(productMapper.toResponseProduct(product)).thenReturn(expectedResponse);
//
//		ProductResponse result = productService.getProduct(id);
//
//		assertEquals(result,expectedResponse);
//	}
//
//	@Test
//	void getProductById_ShowTrow_WhenProductNotExists() {
//		Long id = 1L;
//
//		when(productRepository.findById(id)).thenReturn(Optional.empty());
//
//		assertThrows(ProductNotFoundException.class,()->{
//			productService.getProduct(id);
//		});
//	}
//
//}