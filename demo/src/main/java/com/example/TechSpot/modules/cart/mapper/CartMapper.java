package com.example.TechSpot.modules.cart.mapper;


import com.example.TechSpot.modules.cart.dto.response.CartResponse;
import com.example.TechSpot.modules.cart.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = CartItemMapper.class)
public interface CartMapper {


	@Mapping(target = "cartItemResponses", source = "cartItems")
	CartResponse toCart(Cart cart);
}
