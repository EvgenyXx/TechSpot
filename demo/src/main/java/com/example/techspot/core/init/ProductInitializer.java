package com.example.techspot.core.init;

import com.example.techspot.modules.categories.domain.entity.Category;
import com.example.techspot.modules.categories.infrastructure.repository.CategoryRepository;
import com.example.techspot.modules.products.domain.entity.Product;
import com.example.techspot.modules.products.infrastructure.repository.ProductRepository;
import com.example.techspot.modules.users.domain.entity.User;
import com.example.techspot.modules.users.infratructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductInitializer {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;

	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	@Order(4) // После создания категорий и пользователей
	public void createDemoProducts() {
		log.info("=== СОЗДАНИЕ ТЕСТОВЫХ ТОВАРОВ ===");

		// Ищем существующие категории
		Optional<Category> electronicsCategory = categoryRepository.findByName("Электроника");
		Optional<Category> phonesCategory = categoryRepository.findByName("Смартфоны");
		Optional<Category> laptopsCategory = categoryRepository.findByName("Ноутбуки");

		// Ищем админа как продавца
		Optional<User> adminUser = userRepository.findByEmail("admin@techspot.com");

		if (electronicsCategory.isEmpty() || adminUser.isEmpty()) {
			log.warn("Не найдены необходимые категории или пользователь. Пропускаем создание товаров.");
			return;
		}

		List<Product> demoProducts = List.of(
				// Смартфоны
				Product.builder()
						.productName("iPhone 15 Pro")
						.price(BigDecimal.valueOf(99990))
						.quantity(25)
						.description("Новый iPhone 15 Pro с процессором A17 Pro и камерой 48 МП")
						.category(phonesCategory.orElse(electronicsCategory.get()))
						.user(adminUser.get())
						.build(),

				Product.builder()
						.productName("Samsung Galaxy S24")
						.price(BigDecimal.valueOf(84990))
						.quantity(30)
						.description("Флагманский смартфон Samsung с ИИ-функциями")
						.category(phonesCategory.orElse(electronicsCategory.get()))
						.user(adminUser.get())
						.build(),

				Product.builder()
						.productName("Xiaomi Redmi Note 13")
						.price(BigDecimal.valueOf(24990))
						.quantity(50)
						.description("Бюджетный смартфон с хорошей камерой и батареей")
						.category(phonesCategory.orElse(electronicsCategory.get()))
						.user(adminUser.get())
						.build(),

				// Ноутбуки
				Product.builder()
						.productName("MacBook Air M2")
						.price(BigDecimal.valueOf(119990))
						.quantity(15)
						.description("Ультрабук от Apple с процессором M2")
						.category(laptopsCategory.orElse(electronicsCategory.get()))
						.user(adminUser.get())
						.build(),

				Product.builder()
						.productName("ASUS ROG Strix G16")
						.price(BigDecimal.valueOf(149990))
						.quantity(10)
						.description("Игровой ноутбук с RTX 4060 и процессором Intel i7")
						.category(laptopsCategory.orElse(electronicsCategory.get()))
						.user(adminUser.get())
						.build(),

				Product.builder()
						.productName("Lenovo IdeaPad 5")
						.price(BigDecimal.valueOf(54990))
						.quantity(40)
						.description("Бюджетный ноутбук для работы и учебы")
						.category(laptopsCategory.orElse(electronicsCategory.get()))
						.user(adminUser.get())
						.build(),

				// Аксессуары
				Product.builder()
						.productName("Чехол для iPhone 15 Pro")
						.price(BigDecimal.valueOf(2490))
						.quantity(100)
						.description("Защитный силиконовый чехол с матовой поверхностью")
						.category(electronicsCategory.get())
						.user(adminUser.get())
						.build(),

				Product.builder()
						.productName("Беспроводные наушники Sony WH-1000XM5")
						.price(BigDecimal.valueOf(29990))
						.quantity(35)
						.description("Флагманские наушники с активным шумоподавлением")
						.category(electronicsCategory.get())
						.user(adminUser.get())
						.build(),

				Product.builder()
						.productName("Зарядное устройство Anker 65W")
						.price(BigDecimal.valueOf(3990))
						.quantity(80)
						.description("Компактная зарядка GaN для ноутбука и телефона")
						.category(electronicsCategory.get())
						.user(adminUser.get())
						.build(),

				Product.builder()
						.productName("Внешний SSD Samsung T7 1TB")
						.price(BigDecimal.valueOf(8990))
						.quantity(45)
						.description("Портативный SSD с скоростью до 1050 МБ/с")
						.category(electronicsCategory.get())
						.user(adminUser.get())
						.build()
		);

		// Сохраняем только если товаров еще нет
		long existingCount = productRepository.count();
		if (existingCount == 0) {
			productRepository.saveAll(demoProducts);
			log.info("Создано {} тестовых товаров", demoProducts.size());
		} else {
			log.info("Товары уже существуют ({} шт.). Пропускаем создание.", existingCount);
		}

		log.info("=== ==================== ===");
	}
}