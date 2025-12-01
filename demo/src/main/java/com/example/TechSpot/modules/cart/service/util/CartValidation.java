package com.example.TechSpot.modules.cart.service.util;


import com.example.TechSpot.modules.cart.entity.Cart;
import com.example.TechSpot.modules.cart.entity.CartItems;
import com.example.TechSpot.modules.products.entity.Product;
import com.example.TechSpot.modules.products.exception.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Log4j2
@Component
public class CartValidation {


	public void validateProductQuantity(Product product, int requestedQuantity, Cart cart) {
		log.debug("Проверка доступности товара для добавления: productId={}, quantity={}",
				product.getId(), requestedQuantity);

		int currentInCart = cart.getCartItems().stream()
				.filter(item -> item.getProduct().getId().equals(product.getId()))
				.findFirst()
				.map(CartItems::getQuantity)
				.orElse(0);

		int totalInCartAfterAdd = currentInCart + requestedQuantity;

		if (totalInCartAfterAdd > product.getQuantity()) {
			log.warn("Превышение доступного количества товара. productId={}, в наличии={}, запрошено={}",
					product.getId(), product.getQuantity(), totalInCartAfterAdd);
			throw new InsufficientStockException();
		}

		if (product.getQuantity() == 0) {
			log.warn("Товар отсутствует в наличии. productId={}", product.getId());
			throw new InsufficientStockException();
		}

		log.debug("Проверка количества товара пройдена: productId={}", product.getId());
	}
}
