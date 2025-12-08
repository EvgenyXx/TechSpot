package com.example.techspot.modules.orders.application.mapper;


import com.example.techspot.modules.orders.application.dto.OrderResponse;
import com.example.techspot.modules.orders.domain.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	OrderResponse toOrderResponse(Order order);
}
