package com.example.TechSpot.service.order;

import com.example.TechSpot.entity.*;
import com.example.TechSpot.exception.product.InsufficientStockException;
import com.example.TechSpot.service.cart.CommandCartService;
import com.example.TechSpot.service.product.ProductInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderCreationService {

	private final ProductInventoryService productInventoryService;


	public void updateProductQuantities(Cart cart) {
		log.info("Обновление количеств товаров для корзины");

		for (CartItems cartItem : cart.getCartItems()) {
			log.debug("Обновление количества для товара: {}, количество: {}",
					cartItem.getProduct().getId(), cartItem.getQuantity());

			boolean success = productInventoryService.reduceQuantity(
					cartItem.getProduct().getId(),
					cartItem.getQuantity()
			);

			if (!success) {
				log.error("Недостаточно товара на складе. Товар: {}, запрошено: {}",
						cartItem.getProduct().getId(), cartItem.getQuantity());
				throw new InsufficientStockException();
			}
		}
		log.info("Количества товаров успешно обновлены");
	}

	public Order createOrderFromCart(User user, Cart cart) {
		log.info("Создание заказа из корзины для пользователя: {}", user.getId());

		String orderNumber = generateOrderNumber();
		log.debug("Сгенерирован номер заказа: {}", orderNumber);

		Order order = Order.builder()
				.orderNumber(orderNumber)
				.user(user)
				.orderStatus(OrderStatus.CREATED)
				.totalPrice(cart.getTotalPrice())
				.orderItems(new HashSet<>())
				.build();

		for (CartItems cartItem : cart.getCartItems()) {
			OrderItems orderItem = OrderItems.builder()
					.order(order)
					.product(cartItem.getProduct())
					.productName(cartItem.getProduct().getProductName())
					.itemPrice(cartItem.getProduct().getPrice())
					.quantity(cartItem.getQuantity())
					.build();

			order.getOrderItems().add(orderItem);
			log.debug("Добавлен товар в заказ: {}, количество: {}",
					cartItem.getProduct().getProductName(), cartItem.getQuantity());
		}

		log.info("Заказ создан. Количество позиций: {}, общая сумма: {}",
				order.getOrderItems().size(), order.getTotalPrice());
		return order;
	}

	private String generateOrderNumber() {
		String timestamp = Instant.now().toEpochMilli() + "";
		String random = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
		String orderNumber = "ORD-" + timestamp.substring(0, 10) + "-" + random;
		log.debug("Сгенерирован номер заказа: {}", orderNumber);
		return orderNumber;
	}

}
