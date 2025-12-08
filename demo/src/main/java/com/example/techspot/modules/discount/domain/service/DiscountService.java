package com.example.techspot.modules.discount.domain.service;

import com.example.techspot.modules.api.discount.DiscountProvider;
import com.example.techspot.modules.discount.application.strategy.DiscountStrategy;
import com.example.techspot.modules.discount.domain.entity.Discount;
import com.example.techspot.modules.discount.infrastructure.DiscountRepository;
import com.example.techspot.modules.products.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class DiscountService implements DiscountProvider {

	private final DiscountRepository discountRepository;
	private final List<DiscountStrategy> strategies;

	@Override
	public BigDecimal applyDiscount(Product product, int quantity, BigDecimal basePrice) {


		log.debug(
				"Запуск DiscountService: productId={}, quantity={}, basePrice={}",
				product.getId(), quantity, basePrice
		);

		List<Discount> discounts = discountRepository.findAll().stream()
				.filter(Discount::isActive)
				.filter(this::isValidNow)
				.filter(d -> isMatching(d, product))
				.toList();

		// Важно: INFO — только ключевая бизнес-информация
		log.info(
				"Найдено {} скидок для productId={}",
				discounts.size(), product.getId()
		);

		BigDecimal result = basePrice;

		for (Discount discount : discounts) {

			log.debug(
					"Обработка скидки discountId={}, type={}, percent={}, fixed={}",
					discount.getId(), discount.getType(),
					discount.getPercentage(), discount.getFixedAmount()
			);

			for (DiscountStrategy strategy : strategies) {
				if (strategy.supports(discount)) {

					log.debug(
							"Стратегия {} применяется к discountId={}",
							strategy.getClass().getSimpleName(), discount.getId()
					);

					BigDecimal old = result;
					result = strategy.apply(result, discount, quantity);

					log.debug("Результат: {} → {}", old, result);
				}
			}
		}

		log.info(
				"Итоговая цена после всех скидок productId={} = {}",
				product.getId(), result
		);

		return result;
	}

	private boolean isMatching(Discount discount, Product product) {
		return switch (discount.getType()) {
			case PRODUCT -> discount.getProduct() != null &&
					discount.getProduct().getId().equals(product.getId());
			case CATEGORY -> discount.getCategory() != null &&
					discount.getCategory().getId().equals(product.getCategory().getId());
			case QUANTITY -> discount.getProduct() != null
					&& discount.getProduct().getId().equals(product.getId())
					&& discount.getMinQuantity() != null;
			case GLOBAL -> true;
		};
	}

	private boolean isValidNow(Discount discount) {
		LocalDateTime now = LocalDateTime.now();
		return (discount.getStartsAt() == null || !now.isBefore(discount.getStartsAt())) &&
				(discount.getEndsAt() == null || !now.isAfter(discount.getEndsAt()));
	}
}
