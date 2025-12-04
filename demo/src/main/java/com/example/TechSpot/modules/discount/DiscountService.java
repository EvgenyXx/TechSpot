package com.example.TechSpot.modules.discount;

import com.example.TechSpot.modules.api.discount.DiscountProvider;
import com.example.TechSpot.modules.products.entity.Product;
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

		log.info("➡️ Запуск применения скидок: productId={}, quantity={}, basePrice={}",
				product.getId(), quantity, basePrice);

		List<Discount> discounts = discountRepository.findAll().stream()
				.filter(Discount::isActive)
				.filter(this::isValidNow)
				.filter(d -> isMatching(d, product))
				.toList();

		log.info("🔍 Найдено {} релевантных скидок для Product ID {}",
				discounts.size(), product.getId());

		BigDecimal result = basePrice;

		for (Discount discount : discounts) {

			log.info("➡️ Проверка скидки ID={}, type={}, percent={}, fixed={}",
					discount.getId(),
					discount.getType(),
					discount.getPercentage(),
					discount.getFixedAmount()
			);

			for (DiscountStrategy strategy : strategies) {
				if (strategy.supports(discount)) {

					log.info("✔️ Стратегия '{}' поддерживает эту скидку",
							strategy.getClass().getSimpleName());

					BigDecimal old = result;
					result = strategy.apply(result, discount, quantity);

					log.info("💸 Цена после применения стратегии: {} → {}",
							old, result);
				}
			}
		}

		log.info("🏁 Итоговая цена после всех скидок: {}", result);
		return result;
	}

	private boolean isMatching(Discount d, Product product) {
		return switch (d.getType()) {
			case PRODUCT -> d.getProduct() != null
					&& d.getProduct().getId().equals(product.getId());
			case CATEGORY -> d.getCategory() != null
					&& d.getCategory().getId().equals(product.getCategory().getId());
			case GLOBAL -> true;
		};
	}

	private boolean isValidNow(Discount d) {
		LocalDateTime now = LocalDateTime.now();
		return (d.getStartsAt() == null || !now.isBefore(d.getStartsAt())) &&
				(d.getEndsAt() == null || !now.isAfter(d.getEndsAt()));
	}
}

