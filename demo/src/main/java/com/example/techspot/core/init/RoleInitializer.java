package com.example.techspot.core.init;


import com.example.techspot.modules.users.domain.entity.AppRole;
import com.example.techspot.modules.users.domain.entity.Role;
import com.example.techspot.modules.users.infratructure.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class RoleInitializer {

	private final RoleRepository roleRepository;

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	@Order(1)
	public void roleInitializer() {
		List<Role> rolesToCreate = Arrays.stream(AppRole.values())
				.map(appRole -> Role.builder()
						.name(appRole.getAuthority())
						.build())
				.toList();

		roleRepository.saveAll(rolesToCreate);
		log.info("Созданы все системные роли: {}",
				rolesToCreate.stream().map(Role::getName).toList());
	}
}
