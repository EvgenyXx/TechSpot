package com.example.techspot.modules.users.domain.service;

import com.example.techspot.modules.users.domain.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserStatusDomainService {

	public void changeStatus(User user, boolean isActive) {

		// 1. Проверка — нужно ли вообще менять статус
		if (user.isActive() == isActive) {
			log.warn("Пользователь {} уже имеет статус active={}", user.getId(), isActive);
			return;
		}

		// 2. Применяем изменение
		if (isActive) {
			log.info("Разблокировка пользователя {}", user.getId());
			user.setActive(true);
		} else {
			log.warn("Блокировка пользователя {}", user.getId());
			user.setActive(false);
		}
	}
}
