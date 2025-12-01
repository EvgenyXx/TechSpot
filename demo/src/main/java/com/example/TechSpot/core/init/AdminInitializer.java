package com.example.TechSpot.core.init;



import com.example.TechSpot.modules.cart.service.util.CartInitializer;
import com.example.TechSpot.modules.users.entity.User;
import com.example.TechSpot.modules.users.repository.UserRepository;
import com.example.TechSpot.modules.users.service.query.RoleQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

@RequiredArgsConstructor
@Log4j2
public class AdminInitializer {
	private final PasswordEncoder passwordEncoder;
	private final RoleQueryService roleQueryService;
	private final UserRepository userRepository;
	private final CartInitializer cartInitializer;


	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	@Order(2)
	public void createDefaultAdmin() {
		log.info("=== СОЗДАНИЕ АДМИНА ===");

		String rawPassword = "admin123";
		String encodedPassword = passwordEncoder.encode(rawPassword);


		User admin = User.builder()
				.firstname("admin")
				.lastname("admin")
				.phoneNumber("+79182331822")
				.email("admin@techspot.com")
				.isActive(true)
				.hashPassword(encodedPassword)
				.roles(roleQueryService.getAllRoles())
				.build();
		admin.setCart(cartInitializer.createDefaultCart());

		log.warn("ADMIN {}",admin);

		User saved = userRepository.save(admin);

		// ✅ ПРОВЕРКА ПАРОЛЯ
		boolean passwordMatches = passwordEncoder.matches(rawPassword, saved.getHashPassword());
		log.info("Пароль проверен: {}", passwordMatches ? "СОВПАДАЕТ" : "НЕ СОВПАДАЕТ");
		log.info("Raw password: {}", rawPassword);
		log.info("Encoded password: {}", saved.getHashPassword());
		log.info("=== ========= ===");
	}
}
