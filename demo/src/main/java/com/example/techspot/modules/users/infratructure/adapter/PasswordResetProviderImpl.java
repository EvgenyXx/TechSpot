package com.example.techspot.modules.users.infratructure.adapter;

import com.example.techspot.modules.api.user.PasswordResetProvider;
import com.example.techspot.modules.auth.exception.InvalidResetCodeException;
import com.example.techspot.modules.auth.exception.ResetCodeExpiredException;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.infratructure.repository.UserRepository;
import com.example.techspot.modules.users.application.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;


@Service
@RequiredArgsConstructor
@Log4j2
public class PasswordResetProviderImpl implements PasswordResetProvider {


	private final UserQueryService userQueryService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;


	@Override
	public String generateAndSetResetCode(String email) {
		User user = userQueryService.findByEmail(email);

		int randomNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);
		user.setPasswordResetCode(String.valueOf(randomNumber));
		user.setResetCodeExpiry(LocalDateTime.now().plusMinutes(15));

		userRepository.save(user);

		return String.valueOf(randomNumber);
	}

	@Override
	public void resetPassword(String email, String resetCode, String newPassword) {
		User user = userQueryService.findByEmail(email);
		validateResetCode(email, resetCode);
		String newHashPassword = passwordEncoder.encode(newPassword);
		user.setHashPassword(newHashPassword);
		user.setPasswordResetCode(null);
		user.setResetCodeExpiry(null);

		userRepository.save(user);

	}

	@Override
	public void validateResetCode(String email, String resetCode) {
		User user = userQueryService.findByEmail(email);
		if (user.getPasswordResetCode() == null || !user.getPasswordResetCode().equals(resetCode)) {
			log.warn("Неверный код сброса для пользователя: {}. Ожидался: {}, Получен: {}",
					user.getId(), user.getPasswordResetCode(), resetCode);
			throw new InvalidResetCodeException();
		}

		if (user.getResetCodeExpiry().isBefore(LocalDateTime.now())) {
			log.warn("Код сброса пароля просрочен для пользователя: {}. Время истечения: {}",
					user.getId(), user.getResetCodeExpiry());
			throw new ResetCodeExpiredException();
		}
	}
}
