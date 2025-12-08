package com.example.techspot.modules.discount.application.mapper;

import com.example.techspot.modules.discount.application.dto.CreateDiscountRequest;
import com.example.techspot.modules.discount.application.dto.DiscountResponse;
import com.example.techspot.modules.discount.domain.entity.Discount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

	Discount toEntity(CreateDiscountRequest discountRequest);

	DiscountResponse toResponse(Discount discount);
}
