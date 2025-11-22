package com.example.TechSpot.service.user.password;


import com.example.TechSpot.dto.password.ResetPasswordRequest;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.exception.InvalidResetCodeException;
import com.example.TechSpot.exception.ResetCodeExpiredException;
import com.example.TechSpot.repository.UserRepository;
import com.example.TechSpot.service.user.query.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Log4j2
public class PasswordResetService {

	private final UserFinder finder;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;



	@Transactional
	public void forgotPassword(String email) {
		log.info("Запрос на сброс пароля для email: {}", email);
		User user = finder.findByEmail(email);

		int randomNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);
		user.setPasswordResetCode(String.valueOf(randomNumber));
		user.setResetCodeExpiry(LocalDateTime.now().plusMinutes(15));

		userRepository.save(user);
		log.info("Сгенерирован код сброса пароля: {} для пользователя: {}", randomNumber, user.getId());
		log.info("Код действителен до: {}", user.getResetCodeExpiry());
		//todo add send email
	}

	@Transactional
	public void resetPassword(ResetPasswordRequest request) {
		log.info("Попытка сброса пароля для email: {}", request.email());
		User user = finder.findByEmail(request.email());

		if (user.getPasswordResetCode() == null || !user.getPasswordResetCode().equals(request.resetCode())) {
			log.warn("Неверный код сброса для пользователя: {}. Ожидался: {}, Получен: {}",
					user.getId(), user.getPasswordResetCode(), request.resetCode());
			throw new InvalidResetCodeException();
		}

		if (user.getResetCodeExpiry().isBefore(LocalDateTime.now())) {
			log.warn("Код сброса пароля просрочен для пользователя: {}. Время истечения: {}",
					user.getId(), user.getResetCodeExpiry());
			throw new ResetCodeExpiredException();
		}

		String newHashPassword = passwordEncoder.encode(request.newPassword());
		user.setHashPassword(newHashPassword);
		user.setPasswordResetCode(null);
		user.setResetCodeExpiry(null);

		userRepository.save(user);
		log.info("Пароль успешно сброшен для пользователя: {}", user.getId());
	}

	@Scheduled(fixedRate = 3600000)
	private void cleanExpiredCodes() {
		log.info("Запуск очистки просроченных кодов сброса пароля");
		userRepository.clearExpiredResetCodes(LocalDateTime.now());
		log.info("Очищено просроченных кодов ");
	}
}
