package com.example.techspot.modules.discount.application.comand;


import com.example.techspot.modules.discount.application.dto.CreateDiscountRequest;
import com.example.techspot.modules.discount.application.dto.DiscountResponse;
import com.example.techspot.modules.discount.application.factory.DiscountFactory;
import com.example.techspot.modules.discount.application.factory.DiscountResponseFactory;
import com.example.techspot.modules.discount.domain.entity.Discount;
import com.example.techspot.modules.discount.infrastructure.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountCreateService {

	private final DiscountRepository discountRepository;
	private final DiscountResponseFactory discountResponseFactory;
	private final DiscountFactory discountFactory;


	public DiscountResponse create(CreateDiscountRequest request) {

		Discount discount = discountFactory.create(request);

		Discount savedDiscount = discountRepository.save(discount);

		return discountResponseFactory.from(savedDiscount);
	}
}