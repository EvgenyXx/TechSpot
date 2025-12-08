package com.example.techspot.modules.users.application.command;


import com.example.techspot.modules.users.domain.entity.Role;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.domain.service.UserRoleDomainService;
import com.example.techspot.modules.users.domain.service.UserStatusDomainService;
import com.example.techspot.modules.users.infratructure.repository.UserRepository;
import com.example.techspot.modules.users.application.query.RoleQueryService;
import com.example.techspot.modules.users.application.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@RequiredArgsConstructor
@Log4j2
@Service
public class AdminUserCommandService {


	private final UserRepository userRepository;
	private final UserQueryService userQueryService;
	private final RoleQueryService roleQueryService;
	private final UserStatusDomainService userStatusDomainService;
	private final UserRoleDomainService userRoleDomainService;





	@Transactional
	public void toggleUserStatus(UUID userId, boolean isActive) {
		log.info("Запрос на изменение статуса пользователя: userId={}, isActive={}", userId, isActive);
		User user = userQueryService.findById(userId);
		userStatusDomainService.changeStatus(user,isActive);
		userRepository.save(user);
		log.info("Статус пользователя {} успешно изменен на active={}", user.getId(), isActive);
	}




	@Transactional
	public void updateUserRole(UUID userId, String newRole) {
		log.info("Adding role to user: userId={}, role={}", userId, newRole);

		User user = userQueryService.findById(userId);
		Role role = roleQueryService.getUserRoleByName(newRole);

		userRoleDomainService.addRole(user,role);

		log.info("Role added successfully: userId={}, role={}", userId, newRole);
	}

}