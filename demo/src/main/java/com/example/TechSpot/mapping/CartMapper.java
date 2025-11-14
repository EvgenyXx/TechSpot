package com.example.TechSpot.mapping;


import com.example.TechSpot.dto.cart.response.CartResponse;
import com.example.TechSpot.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = CartItemMapper.class)
public interface CartMapper {


	@Mapping(target = "cartItemResponses", source = "cartItems")
	CartResponse toCart(Cart cart);
}
