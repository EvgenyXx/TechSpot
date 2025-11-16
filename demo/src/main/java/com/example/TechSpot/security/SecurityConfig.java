package com.example.TechSpot.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity()
public class SecurityConfig {

	private final CustomUserDetailService userDetailsService;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth -> auth
						// ТОЛЬКО публичные endpoints
						.requestMatchers("/api/v1/auth/**").permitAll()
						.requestMatchers("/api/v1/query/**").permitAll()
						.requestMatchers("/api/v1/categories/**").permitAll()
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

						// ВСЕ остальные требуют аутентификации
						.anyRequest().authenticated()
				)
				.httpBasic(httpBasic -> httpBasic.realmName("API")) // ВКЛЮЧАЕМ Basic Auth
				.formLogin(form -> form.disable())
				.logout(logout -> logout.disable())
				.csrf(csrf -> csrf.disable())
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}