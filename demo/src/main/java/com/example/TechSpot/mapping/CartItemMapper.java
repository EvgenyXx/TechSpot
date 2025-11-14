package com.example.TechSpot.mapping;

import com.example.TechSpot.dto.cart.response.CartItemResponse;
import com.example.TechSpot.entity.CartItems;
import com.example.TechSpot.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

	@Mapping(target = "productName", source = "product",qualifiedByName = "toProductName")
	CartItemResponse toCartItem(CartItems cartItems);


	@Named("toProductName")
	default String toProductName (Product product){
		return product.getProductName();
	}
}
