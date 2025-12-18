package com.example.techspot.modules.orders.infrastructure.adapter;

import com.example.techspot.modules.api.order.OrderStatsProvider;
import com.example.techspot.modules.orders.infrastructure.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderStatsProviderImpl implements OrderStatsProvider {

	private final OrderRepository orderRepository;

	public Double calculateAverageOrdersPerUser(Long totalUsers) {
		log.trace("Расчет среднего количества заказов на пользователя");
		long totalOrders = orderRepository.count();
		double result = (double) totalOrders / totalUsers;
		log.debug("Среднее количество заказов на пользователя: {}", result);
		return result;
	}

	public Double calculateConversionRate(Long totalUsers) {
		log.trace("Расчет конверсии пользователей");
		if (totalUsers == 0) {
			log.debug("Нет пользователей для расчета конверсии");
			return 0.0;
		}

		Long usersWithOrders = orderRepository.countUsersWithAtLeastOneOrder();
		double conversion = (double) usersWithOrders / totalUsers * 100;
		log.debug("Конверсия пользователей: {}%", String.format("%.2f", conversion));
		return conversion;
	}

}