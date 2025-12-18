package com.example.techspot.modules.users.infratructure.adapter;

import com.example.techspot.modules.api.user.UserLoginProvider;
import com.example.techspot.modules.users.application.dto.response.UserResponse;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.application.mapper.UserMapper;
import com.example.techspot.modules.users.application.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
public class UserLoginProviderImpl implements UserLoginProvider {
	private final UserQueryService userQueryService;
	private final UserMapper userMapper;

	@Override
	public User getUserByEmail(String email) {
		return userQueryService.findByEmail(email);
	}

	@Override
	public UserResponse mapToResponse(User user) {

		return userMapper.toResponse(user);
	}
}
