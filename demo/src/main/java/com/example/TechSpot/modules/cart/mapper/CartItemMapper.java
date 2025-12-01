package com.example.TechSpot.modules.cart.mapper;

import com.example.TechSpot.modules.cart.dto.response.CartItemResponse;
import com.example.TechSpot.modules.cart.entity.CartItems;
import com.example.TechSpot.modules.products.entity.Product;
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
