package com.example.TechSpot.service.user;

import com.example.TechSpot.dto.user.UserRequest;
import com.example.TechSpot.entity.User;
import com.example.TechSpot.exception.user.DuplicatePhoneNumberException;
import com.example.TechSpot.exception.user.UserNotFoundException;
import com.example.TechSpot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserFinder {

	private final UserRepository userRepository;

	public User findById(UUID userId){
		log.info("Начался поиск пользователя с id {}",userId);
		return   userRepository
				.findById(userId)
				.orElseThrow(UserNotFoundException::new);
	}

	public User findByEmail(String email ){
		log.info("Начался поиск пользователя по email {}",email);
		return userRepository.findByEmail(email)
				.orElseThrow(UserNotFoundException::new);
	}

	public boolean existsByEmail(String email){
		log.info("Началась проверка уникальности email {}",email);

		boolean isEmailExists = userRepository.existsByEmail(email);

		if (isEmailExists){
			log.debug("Данный email уже используется {}",email);

		}
		return false;
	}

	public void checkingUniquePhoneNumber(String phoneNumber){
		log.info("Началась проверка уникальности номера телефона {}", phoneNumber);
		boolean isExistsPhoneNumber = userRepository.existsByPhoneNumber(phoneNumber);

		if (isExistsPhoneNumber){
			log.warn("Попытка зарегистрироваться с " +
					"существующим номером телефона:{}", phoneNumber);
			throw new DuplicatePhoneNumberException();
		}
	}
}
