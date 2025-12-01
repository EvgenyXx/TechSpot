package com.example.TechSpot.modules.users.service.query;


import com.example.TechSpot.modules.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserStatisticsService {

	private final UserRepository userRepository;


	public Long countUsers(){
		return userRepository.count();
	}


	public Long countActiveUsersAfter(LocalDateTime localDateTime){
		return userRepository.countActiveUsersAfter(localDateTime);
	}


	public Long activeUsersInPeriod(LocalDateTime localDateTime){
		return userRepository.countUsersCreatedAfter(localDateTime);
	}

	public Long countUsersCreatedAfter(LocalDateTime localDateTime){
		return userRepository.countUsersCreatedAfter(localDateTime);
	}


	public Long countByIsActiveTrue(){
		return userRepository.countByIsActiveTrue();
	}


	public Long countByIsActiveFalse(){
		return userRepository.countByIsActiveFalse();
	}


	public Map<String, Long> getRegistrationsByDate(LocalDateTime dateTime) {
		log.trace("Получение регистраций по датам с {}", dateTime);
		return userRepository.countRegistrationsByDay(dateTime)
				.stream()
				.collect(Collectors.toMap(
						objects -> objects[0].toString(),
						o -> (Long) o[1]
				));
	}


	public Map<String, Long> getUsersByRole() {
		log.trace("Получение распределения пользователей по ролям");
		return userRepository.countUsersByRole()
				.stream()
				.collect(Collectors.toMap(
						arr -> (String) arr[0],
						arr -> (Long) arr[1]
				));
	}
}
