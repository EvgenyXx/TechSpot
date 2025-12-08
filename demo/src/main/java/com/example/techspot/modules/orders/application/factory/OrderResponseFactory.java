package com.example.techspot.modules.orders.application.factory;

import com.example.techspot.modules.orders.application.dto.OrderResponse;
import com.example.techspot.modules.orders.domain.entity.Order;
import com.example.techspot.modules.orders.application.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderResponseFactory {

	private final OrderMapper mapper;

	public OrderResponse build(Order order) {
		return mapper.toOrderResponse(order);
	}
}
