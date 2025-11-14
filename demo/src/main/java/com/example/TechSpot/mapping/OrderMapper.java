package com.example.TechSpot.mapping;


import com.example.TechSpot.dto.order.OrderResponse;
import com.example.TechSpot.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	OrderResponse toOrderResponse(Order order);
}
