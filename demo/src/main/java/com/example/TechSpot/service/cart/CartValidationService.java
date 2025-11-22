package com.example.TechSpot.service.cart;


import com.example.TechSpot.entity.Cart;
import com.example.TechSpot.entity.CartItems;
import com.example.TechSpot.entity.Product;
import com.example.TechSpot.exception.product.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Log4j2
@Service
public class CartValidationService {


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
