package com.example.TechSpot.service.init;


import com.example.TechSpot.entity.Role;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminInitializer {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;


	@EventListener(ApplicationReadyEvent.class)
	public void createDefaultAdmin() {
		// Проверяем - если админа нет, создаем
		if (!userRepository.existsByEmail("admin@techspot.com")) {
			User admin = User.builder()
					.firstname("admin")
					.lastname("admin")
					.phoneNumber("+79181339188")
					.email("admin@techspot.com")
					.hashPassword(passwordEncoder.encode("admin123"))
					.role(Set.of(Role.ROLE_ADMIN,Role.ROLE_SELLER))
					.build();
			userRepository.save(admin);
			System.out.println("✅ Админ создан: admin@techspot.com / admin123");
		}
	}
}
