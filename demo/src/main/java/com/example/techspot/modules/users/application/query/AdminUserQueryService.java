package com.example.techspot.modules.users.application.query;

import com.example.techspot.modules.users.application.service.UserPersonalStatisticsService;
import com.example.techspot.modules.users.application.dto.response.UserDetailResponse;
import com.example.techspot.modules.users.application.dto.response.UserPersonalStats;
import com.example.techspot.modules.users.application.dto.response.UserResponse;
import com.example.techspot.modules.users.application.factory.UserDetailResponseFactory;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.application.mapper.UserMapper;
import com.example.techspot.modules.users.domain.service.UserRolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminUserQueryService {

	private final UserMapper userMapper;
	private final UserQueryService userQueryService;
	private final UserPersonalStatisticsService userPersonalStatisticsService;
	private final UserRolesService userRolesService;
	private final UserDetailResponseFactory responseFactory;

	@Transactional(readOnly = true)
	public Page<UserResponse> getAllUsers(Pageable pageable) {
		return userQueryService.findAllUsers(pageable)
				.map(userMapper::toResponse);
	}

	@Transactional(readOnly = true)
	public UserDetailResponse getUserDetail(UUID userId) {

		User user = userQueryService.findById(userId);

		UserPersonalStats personalStats =
				userPersonalStatisticsService.getPersonalStats(userId);

		Set<String> roles = userRolesService.extractRoleNames(user);

		return responseFactory.build(user, roles, personalStats);
	}
}
