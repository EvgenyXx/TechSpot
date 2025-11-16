package com.example.TechSpot.service.init;


import com.example.TechSpot.entity.Role;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.repository.UserRepository;
import com.example.TechSpot.service.user.UserFinder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AdminInitializer {
	private final PasswordEncoder passwordEncoder;
	private final UserFinder userFinder;
	private final UserRepository userRepository;


	@EventListener(ApplicationReadyEvent.class)
	public void createDefaultAdmin() {
		log.info(
				"Создание базового супер пользователя "
		);
		if (!userFinder.existsByEmail("admin@techspot.com")) {
			log.debug(
					"Не удалось создать супер пользователя email уже занят"
			);
			User admin = User.builder()
					.firstname("admin")
					.lastname("admin")
					.phoneNumber("+79181339188")
					.email("admin@techspot.com")
					.hashPassword(passwordEncoder.encode("admin123"))
					.roles(Set.of(Role.ROLE_ADMIN,Role.ROLE_SELLER,Role.ROLE_USER))
					.build();
			userRepository.save(admin);
			log.info(
					"Супер пользователь был успешно создан "
			);
		}
	}
}
