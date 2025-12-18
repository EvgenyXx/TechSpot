package com.example.techspot.modules.cart.application.mapper;


import com.example.techspot.modules.cart.application.dto.response.CartItemResponse;
import com.example.techspot.modules.cart.application.dto.response.CartResponse;
import com.example.techspot.modules.cart.domain.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.Set;

@Mapper(componentModel = "spring",uses = CartItemMapper.class)
public interface CartMapper {


	@Mapping(target = "cartItemResponses", source = "cartItems")
	CartResponse toCart(Cart cart);

	CartResponse toCartResponse(Set<CartItemResponse> items, BigDecimal totalPrice);
}
