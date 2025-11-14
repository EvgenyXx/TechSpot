package com.example.TechSpot.security;

import com.example.TechSpot.entity.User;
import com.example.TechSpot.exception.user.UserNotFoundException;
import com.example.TechSpot.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = customerRepository.findByEmail(username)
				.orElseThrow(UserNotFoundException::new);
		return new CustomUserDetail(
				user.getId(),
				user.getEmail(),
				user.getHashPassword(),
				user.getFirstname(),
				user.getLastname(),
				user.getRole()
		);
	}
}
