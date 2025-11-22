package com.example.TechSpot.mapping;

import com.example.TechSpot.dto.cart.response.CartItemResponse;
import com.example.TechSpot.entity.CartItems;
import com.example.TechSpot.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

	@Mapping(target = "price", source = "product",qualifiedByName = "toPrice")
	@Mapping(target = "productName", source = "product",qualifiedByName = "toProductName")
	CartItemResponse toCartItem(CartItems cartItems);


	@Named("toProductName")
	default String toProductName (Product product){
		return product.getProductName();
	}

	@Named("toPrice")
	default BigDecimal toPrice(Product product){
		return product.getPrice();
	}
}
