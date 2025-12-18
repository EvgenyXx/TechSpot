package com.example.techspot.modules.users.infratructure.adapter;

import com.example.techspot.modules.api.user.UserProvider;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.application.exception.UserNotFoundException;
import com.example.techspot.modules.users.infratructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Log4j2
@RequiredArgsConstructor
public class UserProviderImpl implements UserProvider {

	private final UserRepository userRepository;

	@Override
	public User findById(UUID userId){
		log.info("Начался поиск пользователя с id {}",userId);
		return   userRepository
				.findById(userId)
				.orElseThrow(UserNotFoundException::new);
	}
}
