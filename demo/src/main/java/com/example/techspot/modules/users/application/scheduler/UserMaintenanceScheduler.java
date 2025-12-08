package com.example.techspot.modules.users.application.scheduler;

import com.example.techspot.modules.users.infratructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Log4j2
@RequiredArgsConstructor
public class UserMaintenanceScheduler {

	private final UserRepository userRepository;

	@Scheduled(fixedRate = 3600000) // 1 час
	public void cleanExpiredResetCodes() {
		log.info("Запуск очистки просроченных кодов сброса пароля");
		userRepository.clearExpiredResetCodes(LocalDateTime.now());
		log.info("Очищено просроченных кодов");
	}
}