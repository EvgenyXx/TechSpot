package com.example.TechSpot.modules.users.adapter;

import com.example.TechSpot.modules.api.user.UserSecurityProvider;
import com.example.TechSpot.modules.products.entity.Product;
import com.example.TechSpot.modules.users.entity.Role;
import com.example.TechSpot.modules.users.entity.User;
import com.example.TechSpot.modules.users.service.query.RoleQueryService;
import com.example.TechSpot.modules.users.service.query.UserQueryService;
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
