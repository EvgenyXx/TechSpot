package com.example.TechSpot.service.order;


import com.example.TechSpot.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderFinder {

	private final OrderRepository orderRepository;


	@Transactional(readOnly = true)
	public long totalOrdersForUser(UUID userId){
		log.info("Получаем количество всех заказов пользователя {}",userId);
		long totalOrders = orderRepository.countByUserId(userId);
		log.info("У {} всего {} заказов",userId,totalOrders);
		return totalOrders;
	}

	@Transactional(readOnly = true)
	public Double sumTotalAmountByUserId(UUID userId){
		log.info("Получение суммы всех заказов пользователя {}",userId);
		double totalPriceOrders = orderRepository.sumTotalAmountByUserId(userId);
		log.info("Сумма всех заказов пользователя {} = {} рублей ",userId,totalPriceOrders);
		return totalPriceOrders;
	}

	public Long countUsersWithAtLeastOneOrder(){
		return orderRepository.countUsersWithAtLeastOneOrder();
	}

	public Long countOrders(){
		return orderRepository.count();
	}

}
