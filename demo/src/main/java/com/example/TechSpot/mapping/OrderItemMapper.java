package com.example.TechSpot.mapping;


import com.example.TechSpot.dto.order.OrderItemsResponse;
import com.example.TechSpot.entity.OrderItems;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

	OrderItemsResponse toOrderItemResponse(OrderItems orderItems);
}
