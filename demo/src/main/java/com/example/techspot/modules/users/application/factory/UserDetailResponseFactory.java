package com.example.techspot.modules.users.application.factory;

import com.example.techspot.modules.users.application.dto.response.UserDetailResponse;
import com.example.techspot.modules.users.application.dto.response.UserPersonalStats;
import com.example.techspot.modules.users.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailResponseFactory {

	public UserDetailResponse build(User user, Set<String> roles, UserPersonalStats stats) {
		return new UserDetailResponse(
				user.getId(),
				user.getFirstname(),
				user.getLastname(),
				user.getEmail(),
				user.getPhoneNumber(),
				user.isActive(),
				roles,
				user.getCreatedAt(),
				null,
				stats.totalOrders(),
				stats.totalReviews(),
				stats.totalPriceOrders()
		);
	}
}
