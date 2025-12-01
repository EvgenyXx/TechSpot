package com.example.TechSpot.modules.cart.adapter;

import com.example.TechSpot.modules.api.cart.CartStatsProvider;
import com.example.TechSpot.modules.cart.repository.CartRepository;
import com.example.TechSpot.modules.cart.service.statistics.CartStatisticsService;
import com.example.TechSpot.modules.categories.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Log4j2
public class CartStatsProviderImpl implements CartStatsProvider {

	private final CartRepository cartRepository;

	@Override
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
