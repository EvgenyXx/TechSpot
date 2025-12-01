package com.example.TechSpot.modules.users.service.query;


import com.example.TechSpot.modules.users.entity.AppRole;
import com.example.TechSpot.modules.users.entity.Role;
import com.example.TechSpot.modules.users.exception.RoleNotFoundException;
import com.example.TechSpot.modules.users.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoleQueryService {

	private final RoleRepository roleRepository;

	public Set<Role>getAllRoles(){
		log.info("Получение всех ролей ");
		List<Role> roles = roleRepository.findAll();

		return new HashSet<>(roles);
	}

	public Role getRoleAdmin(){
		log.info("Получение роли администратор ");
		Role roleAdmin = roleRepository.findByName(AppRole.ROLE_ADMIN.getAuthority())
				.orElseThrow(RoleNotFoundException:: new );
		log.info("Роль администратора успешно получена {} ",roleAdmin.getName());
		return roleAdmin;
	}

	public Role getRoleUser(){
		log.info("Получение роли USER ");
		Role roleUser = roleRepository.findByName(AppRole.ROLE_USER.getAuthority())
				.orElseThrow(RoleNotFoundException::new);
		log.info("Роль пользователя успешно получена {}",roleUser.getName());
		return roleUser;
	}

	public Role getUserRoleByName(String roleName){
		log.info("Получение роли по имени {}",roleName);
		Role role = roleRepository.findByName(roleName)
				.orElseThrow(RoleNotFoundException::new);
		log.info("Роль {} успешно получена",roleName);
		return role;
	}
}
