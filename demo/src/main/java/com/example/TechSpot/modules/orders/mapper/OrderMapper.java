package com.example.TechSpot.modules.orders.mapper;


import com.example.TechSpot.modules.orders.dto.OrderResponse;
import com.example.TechSpot.modules.orders.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	OrderResponse toOrderResponse(Order order);
}
