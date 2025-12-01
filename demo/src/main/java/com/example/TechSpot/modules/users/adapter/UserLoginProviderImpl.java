package com.example.TechSpot.modules.users.adapter;

import com.example.TechSpot.modules.api.user.UserLoginProvider;
import com.example.TechSpot.modules.users.dto.response.UserResponse;
import com.example.TechSpot.modules.users.entity.User;
import com.example.TechSpot.modules.users.mapper.UserMapper;
import com.example.TechSpot.modules.users.service.query.UserQueryService;
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
