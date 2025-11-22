package com.example.TechSpot.service.user.auth;

import com.example.TechSpot.dto.user.LoginRequest;
import com.example.TechSpot.dto.user.UserRequest;
import com.example.TechSpot.dto.user.UserResponse;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.exception.user.AccountNotActiveException;
import com.example.TechSpot.exception.user.InvalidPasswordException;
import com.example.TechSpot.mapping.UserMapper;
import com.example.TechSpot.repository.UserRepository;
import com.example.TechSpot.service.init.CartInitializer;
import com.example.TechSpot.service.role.RoleFinder;
import com.example.TechSpot.service.user.query.UserFinder;
import com.example.TechSpot.service.user.validation.UserValidationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserFinder finder;
	private final AuthenticationManager authenticationManager;
	private final RoleFinder roleFinder;
	private final CartInitializer cartInitializer;
	private final UserValidationService userValidationService;

	@Transactional
	public UserResponse register(UserRequest request) {
		log.info("Началась регистрация пользователя с номером: {}", request.phoneNumber());
		userValidationService.checkingUniquePhoneNumber(request.phoneNumber());
		userValidationService.checkingUniqueEmail(request);

		User user = userMapper.toCustomer(request);
		user.setRoles(Set.of(roleFinder.getRoleUser()));
		user.setHashPassword(passwordEncoder.encode(request.password()));
		user.setActive(true);
		user.setCart(cartInitializer.createDefaultCart());

		User savedBuyer = userRepository.save(user);
		log.info("Покупатель успешно прошел регистрацию с ID: {}", savedBuyer.getId());

		return userMapper.toResponse(savedBuyer);
	}



	public UserResponse login(LoginRequest request, HttpServletRequest httpRequest) {
		log.info("Началась авторизация пользователя по email: {}", request.email());

		User user = finder.findByEmail(request.email());

		if (!user.isActive()) {
			log.warn("Попытка входа в неактивный аккаунт: {}", request.email());
			throw new AccountNotActiveException();
		}

		if (!passwordEncoder.matches(request.password(), user.getHashPassword())) {
			log.debug("Введен неверный пароль для пользователя {}", user.getId());
			throw new InvalidPasswordException();
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.email(), request.password())
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		HttpSession session = httpRequest.getSession(true);
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

		log.info("Авторизация окончена успешно для пользователя: {}", user.getId());
		return userMapper.toResponse(user);
	}




}