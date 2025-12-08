package com.example.techspot.modules.orders.infrastructure.adapter;

import com.example.techspot.modules.api.order.UserOrderStatsProvider;
import com.example.techspot.modules.orders.infrastructure.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Log4j2
public class UserOrderStatsProviderImpl implements UserOrderStatsProvider {

	private final OrderRepository orderRepository;

	@Override
	@Transactional
	public Long countOrdersForUser(UUID userId) {
		log.info("Получаем количество всех заказов пользователя {}",userId);
		long totalOrders = orderRepository.countByUserId(userId);
		log.info("У {} всего {} заказов",userId,totalOrders);
		return totalOrders;
	}


	@Override
	@Transactional(readOnly = true)
	public Double sumTotalAmountByUserId(UUID userId){
		log.info("Получение суммы всех заказов пользователя {}",userId);
		double totalPriceOrders = orderRepository.sumTotalAmountByUserId(userId);
		log.info("Сумма всех заказов пользователя {} = {} рублей ",userId,totalPriceOrders);
		return totalPriceOrders;
	}
}
