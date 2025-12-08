package com.example.techspot.modules.users.domain.service;

import com.example.techspot.modules.users.domain.entity.StatisticsPeriod;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StatisticsPeriodService {

	public LocalDateTime resolveStartDate(StatisticsPeriod period) {
		return switch (period) {
			case LAST_YEAR -> LocalDateTime.now().minusYears(1);
			case LAST_7_DAYS -> LocalDateTime.now().minusDays(7);
			case LAST_30_DAYS -> LocalDateTime.now().minusDays(30);
			case LAST_90_DAYS -> LocalDateTime.now().minusDays(90);
		};
	}
}
