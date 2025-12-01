//package com.example.TechSpot.service;
//
//import com.example.TechSpot.modules.auth.users.dto.request.UserRequest;
//import com.example.TechSpot.modules.auth.users.dto.response.UserResponse;
//import com.example.TechSpot.modules.auth.users.User;
//import com.example.TechSpot.exception.user.DuplicateEmailException;
//import com.example.TechSpot.mapping.UserMapper;
//import com.example.TechSpot.repository.CustomerRepository;
//import com.example.TechSpot.service.user.auth.AuthService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class AuthServiceTest {
//
//	// 🔧 Мокаем зависимости (то что AuthService использует внутри)
//	@Mock
//	private CustomerRepository customerRepository;
//
//	@Mock
//	private UserMapper userMapper;
//
//	// 🎯 Тестируемый сервис (в него автоматически вставятся моки)
//	@InjectMocks
//	private AuthService authService;
//
//	// 🧪 Твой первый тест будет здесь
//	@Test
//	void register_ShouldSuccess_WhenValidRequest() {
//		// 🎯 Given - подготавливаем данные
//		// 1. 📝 Тестовый запрос (что приходит в метод)
//		UserRequest request = new UserRequest(
//				"Иван",           // firstname
//				"Петров",         // lastname
//				"ivan@test.com",  // email
//				"+79123456789",   // phoneNumber
//				"password123"     // password
//		);
//
//		// 2. 🗄️ Тестовая сущность (что будет сохраняться в БД)
//		User user = new User();
//		user.setId(UUID.randomUUID());        // сгенерированный ID
//		user.setFirstname("Иван");
//		user.setLastname("Петров");
//		user.setEmail("ivan@test.com");
//		user.setPhoneNumber("+79123456789");
//		user.setHashPassword("password123");  // пока без хеширования
//
//		// 3. 📤 Ожидаемый ответ (что вернет метод)
//		UserResponse expectedResponse = new UserResponse(
//				user.getId(),     // тот же ID что у customer
//				"Иван",              // firstname
//				"Петров",            // lastname
//				"ivan@test.com",     // email
//				"+79123456789"     // phoneNumber
//
//		);
//
//		// 1. ✅ Email свободен
//		when(customerRepository.existsByEmail("ivan@test.com"))
//				.thenReturn(false);
//
//		// 2. ✅ Телефон свободен
//		when(customerRepository.existsByPhoneNumber("+79123456789"))
//				.thenReturn(false);
//
//		// 3. ✅ Маппинг DTO → Entity работает
//		when(userMapper.toCustomer(request))
//				.thenReturn(user);
//
//		// 4. ✅ Сохранение в БД работает
//		when(customerRepository.save(any(User.class)))
//				.thenReturn(user);
//
//		// 5. ✅ Маппинг Entity → DTO работает
//		when(userMapper.toResponse(user))
//				.thenReturn(expectedResponse);
//
//		UserResponse actualResponse = authService.register(request);
//
//		assertNotNull(actualResponse, "Метод должен вернуть не-null результат");
//
//		// 2. Проверяем что вернулся ожидаемый response
//		assertEquals(expectedResponse, actualResponse, "Должен вернуться ожидаемый CustomerResponse");
//
//		// 3. Проверяем что save() вызвался ровно 1 раз
//		verify(customerRepository, times(1)).save(any(User.class));
//
//		// 4. Проверяем что проверка email вызвалась
//		verify(customerRepository).existsByEmail("ivan@test.com");
//
//		// 5. Проверяем что проверка телефона вызвалась
//		verify(customerRepository).existsByPhoneNumber("+79123456789");
//
//		// 6. Проверяем что маппинг DTO → Entity вызвался
//		verify(userMapper).toCustomer(request);
//
//		// 7. Проверяем что маппинг Entity → DTO вызвался
//		verify(userMapper).toResponse(user);
//	}
//
//	@Test
//	void register_ShouldThrowDuplicateEmailException_WhenEmailExists() {
//		// 🎯 Given
//		UserRequest request = new UserRequest(
//				"Иван", "Петров", "existing@test.com", "+79123456789", "password123"
//		);
//
//		// 🔧 Настраиваем моки:
//		when(customerRepository.existsByEmail("existing@test.com"))
//				.thenReturn(true); // Email занят
//		when(customerRepository.existsByPhoneNumber("+79123456789"))
//				.thenReturn(false); // 👈 Разрешаем вызов, но телефон свободен
//
//		// 🎯 When & Then
//		assertThrows(DuplicateEmailException.class, () -> {
//			authService.register(request);
//		});
//
//		// ✅ Проверяем что save() НЕ вызывался
//		verify(customerRepository, never()).save(any(User.class));
//
//		// ✅ Проверяем что ОБЕ проверки вызвались
//		verify(customerRepository).existsByEmail("existing@test.com");
//		verify(customerRepository).existsByPhoneNumber("+79123456789"); // 👈 Убираем never()
//	}
//
//}