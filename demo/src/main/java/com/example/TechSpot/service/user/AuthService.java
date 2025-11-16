package com.example.TechSpot.service.user;


import com.example.TechSpot.dto.user.UserRequest;
import com.example.TechSpot.dto.user.UserResponse;
import com.example.TechSpot.entity.Role;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.exception.user.DuplicateEmailException;
import com.example.TechSpot.exception.user.DuplicatePhoneNumberException;
import com.example.TechSpot.mapping.UserMapper;
import com.example.TechSpot.repository.UserRepository;
import com.example.TechSpot.security.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final CustomUserDetailService userDetailsService;
	private final PasswordEncoder passwordEncoder; // ← ДОБАВЬ ЭТО!

	public UserResponse register(UserRequest request) {
		log.info("Началась регистрация пользователя с номером:{} ", request.phoneNumber());
		checkingUniquePhoneNumber(request);
		checkingUniqueEmail(request);

		User user = userMapper.toCustomer(request);
		user.setRole(Set.of(Role.ROLE_USER));
		user.setHashPassword(passwordEncoder.encode(request.password())); // ← ХЕШИРУЕМ!

		User savedBuyer = userRepository.save(user);
		log.info("Покупатель успешно прошел регистрацию {}", savedBuyer.getId());


		return userMapper.toResponse(savedBuyer);
	}


	private void checkingUniquePhoneNumber(UserRequest userRequest){
		log.info("Началась проверка уникальности номера телефона {}",
				userRequest.phoneNumber());
		if (userRepository.existsByPhoneNumber(userRequest.phoneNumber())){
			log.warn("Попытка зарегистрироваться с " +
					"существующим номером телефона:{}", userRequest.phoneNumber());
			throw new DuplicatePhoneNumberException();
		}
	}

	private void checkingUniqueEmail(UserRequest userRequest){
		log.info("Началась проверка уникальности электронной почти {}",
				userRequest.email());
		if (userRepository.existsByEmail(userRequest.email())){
			log.warn("Попытка зарегистрироваться с " +
					"существующей электронной почтой:{}", userRequest.email());
			throw new DuplicateEmailException();
		}
	}
}
