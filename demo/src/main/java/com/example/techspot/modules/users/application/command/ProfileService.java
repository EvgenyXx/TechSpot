package com.example.techspot.modules.users.application.command;


import com.example.techspot.core.config.CacheNames;
import com.example.techspot.modules.users.application.dto.request.UpdateProfileRequest;
import com.example.techspot.modules.users.application.dto.response.UserResponse;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.application.mapper.UserMapper;
import com.example.techspot.modules.users.infratructure.repository.UserRepository;
import com.example.techspot.modules.users.application.query.UserQueryService;
import com.example.techspot.modules.users.application.validator.UserValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Log4j2
public class ProfileService {

	private final UserMapper userMapper;
	private final UserValidationService userValidationService;
	private final UserQueryService userQueryService;
	private final UserRepository userRepository;


	@Cacheable(value = CacheNames.USER_PROFILES, key = "#userId")
	@Transactional(readOnly = true)
	public UserResponse getCurrentUser(UUID userId) {
		log.info("Получаем данные текущего пользователя {}", userId);
		User user = userQueryService.findById(userId);
		log.info("Текущий пользователь успешно найден {}", userId);
		return userMapper.toResponse(user);
	}

	@CachePut(value = CacheNames.USER_PROFILES, key = "#userId")
	@Transactional
	public UserResponse updateProfile(UUID userId, UpdateProfileRequest request) {
		log.info("Началось обновление профиля пользователя {}", userId);
		User user = userQueryService.findById(userId);

		if (!user.getPhoneNumber().equals(request.phoneNumber())) {
			userValidationService.checkingUniquePhoneNumber(request.phoneNumber());
		}

		userMapper.updateUser(request, user);
		User save = userRepository.save(user);

		log.info("Обновление профиля пользователя успешно завершено {}", userId);
		return userMapper.toResponse(save);
	}
}
