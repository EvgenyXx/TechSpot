package com.example.techspot.modules.auth.service;


import com.example.techspot.core.cache.LoginAttemptCacheService;
import com.example.techspot.modules.api.user.UserLoginProvider;
import com.example.techspot.modules.auth.dto.LoginRequest;
import com.example.techspot.modules.auth.exception.InvalidPasswordException;
import com.example.techspot.modules.users.application.dto.response.UserResponse;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.application.exception.AccountNotActiveException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log4j2
@RequiredArgsConstructor
public class LoginService {

	private final LoginAttemptCacheService loginAttemptCacheService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final UserLoginProvider userLoginProvider;
	private final ApplicationEventPublisher applicationEventPublisher;



	public UserResponse login(LoginRequest request, HttpServletRequest httpRequest) {
		log.info("Началась авторизация пользователя по email: {}", request.email());

		if (loginAttemptCacheService.isBlocked(request.email())){
			log.warn("Аккаунт временно заблокирован: {}", request.email());
//			throw new AccountTemporarilyLockedException();
		}

		User user = userLoginProvider.getUserByEmail(request.email());

		if (!user.isActive()) {
			log.warn("Попытка входа в неактивный аккаунт: {}", request.email());
			throw new AccountNotActiveException();
		}

		if (!passwordEncoder.matches(request.password(), user.getHashPassword())) {
			log.debug("Введен неверный пароль для пользователя {}", user.getId());
			loginAttemptCacheService.loginFailed(request.email());

			throw new InvalidPasswordException();
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.email(), request.password())
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		HttpSession session = httpRequest.getSession(true);
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
		loginAttemptCacheService.loginSuccess(request.email());

		log.info("Авторизация окончена успешно для пользователя: {}", user.getId());
		return userLoginProvider.mapToResponse(user);
	}
}
