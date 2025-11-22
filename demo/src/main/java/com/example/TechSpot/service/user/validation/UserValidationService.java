package com.example.TechSpot.service.user.validation;


import com.example.TechSpot.dto.user.UserRequest;
import com.example.TechSpot.exception.user.DuplicateEmailException;
import com.example.TechSpot.exception.user.DuplicatePhoneNumberException;
import com.example.TechSpot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserValidationService {

	private final UserRepository userRepository;

	public void checkingUniqueEmail(UserRequest userRequest) {
		log.info("Началась проверка уникальности электронной почты: {}", userRequest.email());
		if (userRepository.existsByEmail(userRequest.email())) {
			log.warn("Попытка зарегистрироваться с существующей электронной почтой: {}", userRequest.email());
			throw new DuplicateEmailException();
		}
		log.info("Электронная почта {} уникальна", userRequest.email());
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


	public boolean existsByEmail(String email){
		log.info("Началась проверка уникальности email {}",email);

		boolean isEmailExists = userRepository.existsByEmail(email);

		if (isEmailExists){
			log.debug("Данный email уже используется {}",email);
		}

		return isEmailExists;
	}
}
