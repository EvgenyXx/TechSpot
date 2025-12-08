package com.example.techspot.modules.cart.application.command;


import com.example.techspot.modules.cart.infrastructure.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class CartStatisticsService {

	private final CartRepository cartRepository;

	public Map<String, Long> getCartStatistics() {
		log.trace("Получение статистики корзин");
		return cartRepository.getStat()
				.stream()
				.collect(Collectors.toMap(
						objects -> objects[0].toString(),
						o -> (Long) o[1]
				));
	}
}
