package com.example.TechSpot.service.user.profile;


import com.example.TechSpot.dto.user.UpdateProfileRequest;
import com.example.TechSpot.dto.user.UserResponse;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.mapping.UserMapper;
import com.example.TechSpot.repository.UserRepository;
import com.example.TechSpot.service.user.query.UserFinder;
import com.example.TechSpot.service.user.validation.UserValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Log4j2
public class ProfileService {
	private final UserFinder userFinder;
	private final UserMapper userMapper;
	private final UserValidationService userValidationService;
	private final UserRepository userRepository;


	@Transactional(readOnly = true)
	public UserResponse getCurrentUser(UUID userId) {
		log.info("Получаем данные текущего пользователя {}", userId);
		User user = userFinder.findById(userId);
		log.info("Текущий пользователь успешно найден {}", userId);
		return userMapper.toResponse(user);
	}

	@Transactional
	public UserResponse updateProfile(UUID userId, UpdateProfileRequest request) {
		log.info("Началось обновление профиля пользователя {}", userId);
		User user = userFinder.findById(userId);

		if (!user.getPhoneNumber().equals(request.phoneNumber())) {
			userValidationService.checkingUniquePhoneNumber(request.phoneNumber());
		}

		userMapper.updateUser(request, user);
		User save = userRepository.save(user);

		log.info("Обновление профиля пользователя успешно завершено {}", userId);
		return userMapper.toResponse(save);
	}
}
