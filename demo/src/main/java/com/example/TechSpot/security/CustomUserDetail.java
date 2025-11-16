package com.example.TechSpot.security;

import com.example.TechSpot.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public record CustomUserDetail(UUID id, String email, String password, String firstname, String lastname,
							   Set<Role> roles ) implements UserDetails {

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.name()))
				.collect(Collectors.toList());
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
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

//	@Override
//	public boolean equals(Object o) {
//		if (o == null || getClass() != o.getClass()) return false;
//		CustomUserDetail that = (CustomUserDetail) o;
//		return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(password, that.password)
//				&& Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && role == that.role;
//	}

}
