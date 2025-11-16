package com.example.TechSpot.service.user;

import com.example.TechSpot.entity.User;
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
		log.info(
				"Начался поиск пользователя с id {}",userId
		);
		return   userRepository
				.findById(userId)
				.orElseThrow(UserNotFoundException::new);
	}
}
