package com.example.TechSpot.modules.users.service.command;


import com.example.TechSpot.modules.users.entity.Role;
import com.example.TechSpot.modules.users.entity.User;
import com.example.TechSpot.modules.users.exception.UserAlreadyHasRoleException;
import com.example.TechSpot.modules.users.repository.UserRepository;
import com.example.TechSpot.modules.users.service.query.RoleQueryService;
import com.example.TechSpot.modules.users.service.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@RequiredArgsConstructor
@Log4j2
@Service
public class AdminUserCommandService {


	private final UserRepository userRepository;
	private final UserQueryService userQueryService;
	private final RoleQueryService roleQueryService;




	@Transactional
	public void toggleUserStatus(UUID userId, boolean isActive) {
		log.info("Запрос на изменение статуса пользователя: userId={}, isActive={}", userId, isActive);

		User user = userQueryService.findById(userId);
		log.debug("Найден пользователь: id={}, текущий статус active={}", user.getId(), user.isActive());

		if (user.isActive() == isActive) {
			log.warn("Пользователь {} уже имеет требуемый статус active={}", userId, isActive);
			return;
		}

		if (isActive) {
			log.info("Разблокировка пользователя {}", user.getId());
			user.setActive(true);
		} else {
			log.warn("Блокировка пользователя {}", user.getId());
			user.setActive(false);
		}

		userRepository.save(user);
		log.info("Статус пользователя {} успешно изменен на active={}", user.getId(), isActive);
	}




	@Transactional
	public void updateUserRole(UUID userId, String newRole) {
		log.info("Adding role to user: userId={}, role={}", userId, newRole);

		User user = userQueryService.findById(userId);
		Role role = roleQueryService.getUserRoleByName(newRole);

		if (user.getRoles().contains(role)) {
			log.warn("User already has role: userId={}, role={}", userId, newRole);
			throw new UserAlreadyHasRoleException();
		}

		user.getRoles().add(role);

		log.info("Role added successfully: userId={}, role={}", userId, newRole);
	}

}