package com.example.techspot.modules.users.infratructure.adapter;


import com.example.techspot.modules.api.cart.UserCartProvider;
import com.example.techspot.modules.api.user.UserRegistrationProvider;
import com.example.techspot.modules.users.application.dto.request.UserRequest;
import com.example.techspot.modules.users.application.dto.response.UserResponse;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.application.mapper.UserMapper;
import com.example.techspot.modules.users.infratructure.repository.UserRepository;

import com.example.techspot.modules.users.application.query.RoleQueryService;
import com.example.techspot.modules.users.application.validator.UserValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@RequiredArgsConstructor
@Log4j2
public class UserRegistrationProviderImpl implements UserRegistrationProvider {

	private final UserValidationService userValidationService;
	private final RoleQueryService roleQueryService;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final UserCartProvider userCartProvider;
	private final UserRepository userRepository;


	@Override
	@Transactional
	public UserResponse createUser(UserRequest request) {
		userValidationService.checkingUniqueEmail(request);
		userValidationService.checkingUniquePhoneNumber(request.phoneNumber());
		User user = userMapper.toCustomer(request);
		user.setRoles(Set.of(roleQueryService.getRoleUser()));
		user.setHashPassword(passwordEncoder.encode(request.password()));
		user.setActive(true);
		user.setCart(userCartProvider.createDefaultCart());
		User saveUser = userRepository.save(user);
		return userMapper.toResponse(saveUser);

	}
}
