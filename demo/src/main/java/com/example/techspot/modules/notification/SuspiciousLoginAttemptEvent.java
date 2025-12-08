package com.example.techspot.modules.notification;

import java.time.LocalDateTime;

public record SuspiciousLoginAttemptEvent(
		String email,           // Какой аккаунт атакуют
		String ipAddress,       // Откуда атакуют
		int failedAttempts,     // Сколько попыток
		String userAgent,       // Браузер/устройство
		LocalDateTime timestamp // Когда произошло
) {}