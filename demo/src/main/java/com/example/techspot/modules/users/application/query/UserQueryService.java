package com.example.techspot.modules.users.application.query;

import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.application.exception.UserNotFoundException;
import com.example.techspot.modules.users.infratructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserQueryService {

	private final UserRepository userRepository;




	public Page<User>findAllUsers(Pageable pageable){
		return userRepository.findAll(pageable);
	}

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




}
