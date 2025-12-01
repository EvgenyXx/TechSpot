package com.example.TechSpot.modules.users.service.command;


import com.example.TechSpot.core.config.CacheNames;
import com.example.TechSpot.modules.users.dto.request.UpdateProfileRequest;
import com.example.TechSpot.modules.users.dto.response.UserResponse;
import com.example.TechSpot.modules.users.entity.User;
import com.example.TechSpot.modules.users.mapper.UserMapper;
import com.example.TechSpot.modules.users.repository.UserRepository;
import com.example.TechSpot.modules.users.service.query.UserQueryService;
import com.example.TechSpot.modules.users.service.validation.UserValidationService;
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
