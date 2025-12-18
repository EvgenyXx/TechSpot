package com.example.techspot.modules.users.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActiveUser(
		UUID userId,
		String email,
		Long orderCount,
		Long loginCount,
		LocalDateTime lastActivity
) {}