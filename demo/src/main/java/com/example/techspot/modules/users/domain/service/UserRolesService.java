package com.example.techspot.modules.users.domain.service;

import com.example.techspot.modules.users.domain.entity.Role;
import com.example.techspot.modules.users.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserRolesService {

	public Set<String> extractRoleNames(User user) {
		return user.getRoles()
				.stream()
				.map(Role::getName)
				.collect(Collectors.toSet());
	}
}
