package com.example.techspot.modules.auth.service;

import com.example.techspot.modules.api.user.UserRegistrationProvider;
import com.example.techspot.modules.users.application.dto.request.UserRequest;
import com.example.techspot.modules.users.application.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {


	private final UserRegistrationProvider userRegistrationProvider;



	@Transactional
	public UserResponse register(UserRequest request) {
		log.info("Началась регистрация пользователя с номером: {}", request.phoneNumber());



		UserResponse user = userRegistrationProvider.createUser(request);
		log.info("Покупатель успешно прошел регистрацию с ID: {}", user.id());

		return user;
	}


}