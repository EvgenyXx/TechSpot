package com.example.techspot.modules.users.infratructure.adapter;

import com.example.techspot.modules.api.user.UserSecurityProvider;
import com.example.techspot.modules.users.domain.entity.Role;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.application.query.RoleQueryService;
import com.example.techspot.modules.users.application.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Log4j2
@RequiredArgsConstructor
public class UserSecurityProviderImpl implements UserSecurityProvider {

	private final UserQueryService userQueryService;
	private final RoleQueryService roleQueryService;



	@Override
	public boolean canDeleteProduct(UUID userId, UUID productOwnerId) {
		return isAdmin(userId) || productOwnerId.equals(userId);
	}

	private boolean isAdmin(UUID userId) {
		User user = userQueryService.findById(userId);
		Role adminRole = roleQueryService.getRoleAdmin();
		return user.getRoles().contains(adminRole);
	}
}
