package com.example.techspot.modules.users.application.factory;

import com.example.techspot.modules.users.application.dto.response.UserDetailResponse;
import com.example.techspot.modules.users.application.dto.response.UserDetailedStatistics;
import com.example.techspot.modules.users.application.dto.response.UserStatistics;
import com.example.techspot.modules.users.domain.entity.Role;
import com.example.techspot.modules.users.domain.entity.StatisticsPeriod;
import com.example.techspot.modules.users.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserDTOFactory {

	public UserDetailResponse buildUserDetail(
			User user,
			long totalOrders,
			long totalReviews,
			Double totalPriceOrders
	) {
		return new UserDetailResponse(
				user.getId(),
				user.getFirstname(),
				user.getLastname(),
				user.getEmail(),
				user.getPhoneNumber(),
				user.isActive(),
				user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()),
				user.getCreatedAt(),
				null,
				totalOrders,
				totalReviews,
				totalPriceOrders
		);
	}

	public UserStatistics buildUserStatistics(
			long totalUsers,
			long newUsers,
			long activeUsers,
			long blockedUsers
	) {
		return new UserStatistics(
				totalUsers,
				newUsers,
				activeUsers,
				blockedUsers,
				LocalDateTime.now()
		);
	}

	public UserDetailedStatistics buildDetailedStatistics(
			Long totalUsers,
			Long newUsers,
			Long activeUsers,
			Map<String, Long> registrations,
			Map<String, Long> cartStats,
			Double conversionRate,
			Double avgOrders,
			Map<String, Long> roles,
			StatisticsPeriod period
	) {
		return new UserDetailedStatistics(
				totalUsers,
				newUsers,
				activeUsers,
				registrations,
				cartStats,
				conversionRate,
				avgOrders,
				null,
				roles,
				null,
				period
		);
	}
}
