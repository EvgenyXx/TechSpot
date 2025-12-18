package com.example.techspot.core.security;


import com.example.techspot.modules.users.domain.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public record CustomUserDetail(UUID id,
							   String email,
							   String password,
							   String firstname,
							   String lastname,
							   Set<Role> roleV1s,
							   boolean isActive) implements UserDetails {

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roleV1s.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.toList();
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.isActive;
	}


}
