package com.example.TechSpot.service.user;


import com.example.TechSpot.dto.user.LoginRequest;
import com.example.TechSpot.dto.user.UpdateProfileRequest;
import com.example.TechSpot.dto.user.UserRequest;
import com.example.TechSpot.dto.user.UserResponse;
import com.example.TechSpot.entity.Role;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.exception.user.DuplicateEmailException;
import com.example.TechSpot.exception.user.DuplicatePhoneNumberException;
import com.example.TechSpot.exception.user.InvalidPasswordException;
import com.example.TechSpot.mapping.UserMapper;
import com.example.TechSpot.repository.UserRepository;
import com.example.TechSpot.security.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final CustomUserDetailService userDetailsService;
	private final PasswordEncoder passwordEncoder; // ← ДОБАВЬ ЭТО!
	private final UserFinder finder;

	public UserResponse register(UserRequest request) {
		log.info("Началась регистрация пользователя с номером:{} ", request.phoneNumber());
		finder.checkingUniquePhoneNumber(request.phoneNumber());
		checkingUniqueEmail(request);

		User user = userMapper.toCustomer(request);
		user.setRoles(Set.of(Role.ROLE_USER));
		user.setHashPassword(passwordEncoder.encode(request.password())); // ← ХЕШИРУЕМ!

		User savedBuyer = userRepository.save(user);
		log.info("Покупатель успешно прошел регистрацию {}", savedBuyer.getId());


		return userMapper.toResponse(savedBuyer);
	}



	private void checkingUniqueEmail(UserRequest userRequest){
		log.info("Началась проверка уникальности электронной почти {}", userRequest.email());
		if (userRepository.existsByEmail(userRequest.email())){
			log.warn("Попытка зарегистрироваться с " + "существующей электронной почтой:{}", userRequest.email());
			throw new DuplicateEmailException();
		}
	}


	public UserResponse login(LoginRequest request){
		log.info("Началась авторизация пользователя по email {}",request.email());
		User user = finder.findByEmail(request.email());

		if (!passwordEncoder.matches(request.password(), user.getHashPassword())){
			log.debug("Введен не верный пароль для пользователя {}",user.getId());
			throw new InvalidPasswordException();
		}
		log.info(
				"Авторизация окончена успешно"
		);
		//todo add trow isActiv
		return userMapper.toResponse(user);
	}

	public UserResponse getCurrentUser(UUID userId){
		log.info("Получаем данные текущего пользователя {}",userId);
		User user = finder.findById(userId);
		log.info("Текущий пользователь успешно найден {}",userId);
		return userMapper.toResponse(user);
	}


	public UserResponse updateProfile(UUID userId, UpdateProfileRequest request) {
		log.info("Началось обновление профиля пользователя {}", userId);

		User user = finder.findById(userId);


		if (!user.getPhoneNumber().equals(request.phoneNumber())) {
			finder.checkingUniquePhoneNumber(request.phoneNumber());
		}

		userMapper.updateUser(request, user);
		User save = userRepository.save(user);

		log.info("Обновление профиля пользователя успешно завершено {}", userId);
		return userMapper.toResponse(save);
	}

}
