package com.example.techspot.core.init;



import com.example.techspot.modules.cart.domain.service.CartInitializer;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.infratructure.repository.UserRepository;
import com.example.techspot.modules.users.application.query.RoleQueryService;

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
	@Order(1)
	public void createDefaultAdmin() {
		log.info("=== СОЗДАНИЕ АДМИНА ===");

		// ✅ ПРОВЕРКА 1: Админ уже существует?
		if (userRepository.findByEmail("admin@techspot.com").isPresent()) {
			log.info("Админ уже существует, пропускаем создание");
			return;
		}

		// ✅ ПРОВЕРКА 2: Роли загрузились из БД?
		var roles = roleQueryService.getAllRoles();
		if (roles.isEmpty()) {
			log.error("Роли не найдены в БД! Сначала запусти миграции с ролями.");
			return; // или кидай исключение
		}

		String rawPassword = "admin123";
		String encodedPassword = passwordEncoder.encode(rawPassword);


		User admin = User.builder()
				.firstname("admin")
				.lastname("admin")
				.phoneNumber("+79182331822")
				.email("admin@techspot.com")
				.isActive(true)
				.hashPassword(encodedPassword)
				.roles(roles) // ← теперь roles гарантированно не пустые
				.build();
		admin.setCart(cartInitializer.createDefaultCart());

		User saved = userRepository.save(admin);

		log.info("Админ создан успешно. ID: {}", saved.getId());
	}
}
