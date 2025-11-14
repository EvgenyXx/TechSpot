package com.example.TechSpot.service.user;

import com.example.TechSpot.entity.User;
import com.example.TechSpot.exception.user.UserNotFoundException;
import com.example.TechSpot.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserFinder {

	private final CustomerRepository customerRepository;

	public User findById(UUID userId){
		log.info(
				"Начался поиск пользователя с id {}",userId
		);
		return   customerRepository
				.findById(userId)
				.orElseThrow(UserNotFoundException::new);
	}
}
