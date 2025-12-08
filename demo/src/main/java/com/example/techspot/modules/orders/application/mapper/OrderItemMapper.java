package com.example.techspot.modules.orders.application.mapper;


import com.example.techspot.modules.orders.application.dto.OrderItemsResponse;
import com.example.techspot.modules.orders.domain.entity.OrderItems;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

	OrderItemsResponse toOrderItemResponse(OrderItems orderItems);
}
