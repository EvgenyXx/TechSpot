package com.example.TechSpot.modules.auth.service;


import com.example.TechSpot.modules.api.user.PasswordResetProvider;
import com.example.TechSpot.modules.auth.dto.ResetPasswordRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Log4j2
public class PasswordResetService {


	private final PasswordResetProvider passwordResetProvider;




	@Transactional
	public void forgotPassword(String email) {
		log.info("Запрос на сброс пароля для email: {}", email);
		String resetCode = passwordResetProvider.generateAndSetResetCode(email);

		log.info("Сгенерирован код сброса пароля: {} для пользователя: ", resetCode);
		//todo add send email
	}

	@Transactional
	public void resetPassword(ResetPasswordRequest request) {
		log.info("Попытка сброса пароля для email: {}", request.email());

		passwordResetProvider.resetPassword(request.email(), request.resetCode(), request.newPassword());
		log.info("Пароль успешно сброшен для пользователя: {}", request.email());
	}


}
