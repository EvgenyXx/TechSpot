package com.example.TechSpot.modules.orders.mapper;


import com.example.TechSpot.modules.orders.dto.OrderItemsResponse;
import com.example.TechSpot.modules.orders.entity.OrderItems;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

	OrderItemsResponse toOrderItemResponse(OrderItems orderItems);
}
