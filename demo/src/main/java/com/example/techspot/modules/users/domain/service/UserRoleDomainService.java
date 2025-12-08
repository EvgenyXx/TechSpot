package com.example.techspot.modules.users.domain.service;

import com.example.techspot.modules.users.domain.entity.Role;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.application.exception.UserAlreadyHasRoleException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserRoleDomainService {

	public void addRole(User user, Role role) {

		// 1. Проверка бизнес‑правила
		if (user.getRoles().contains(role)) {
			log.warn("Пользователь {} уже имеет роль {}", user.getId(), role.getName());
			throw new UserAlreadyHasRoleException();
		}

		// 2. Применяем изменение
		user.getRoles().add(role);
		log.info("Пользователю {} добавлена роль {}", user.getId(), role.getName());
	}
}
