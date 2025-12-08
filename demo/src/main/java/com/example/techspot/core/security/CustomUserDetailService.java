package com.example.techspot.core.security;

import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.application.exception.UserNotFoundException;
import com.example.techspot.modules.users.infratructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(username)
				.orElseThrow(UserNotFoundException::new);

		return new CustomUserDetail(
				user.getId(),
				user.getEmail(),
				user.getHashPassword(),
				user.getFirstname(),
				user.getLastname(),
				user.getRoles(),
				user.isActive()
		);
	}
}
