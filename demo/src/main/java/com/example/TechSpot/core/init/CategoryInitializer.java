package com.example.TechSpot.core.init;

import com.example.TechSpot.modules.categories.entity.Category;
import com.example.TechSpot.modules.categories.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Log4j2
public class CategoryInitializer {

	private final CategoryRepository categoryRepository;

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	@Order(3)
	public void initCategories() {
		if (categoryRepository.count() > 0) {
			log.info("Категории уже существуют, пропускаем инициализацию");
			return;
		}

		log.info("Начало инициализации категорий...");


		Category electronics = createCategory("Электроника", "electronics", null);
		Category clothing = createCategory("Одежда", "clothing", null);
		Category books = createCategory("Книги", "books", null);


		Category smartphones = createCategory("Смартфоны", "smartphones", electronics);
		Category laptops = createCategory("Ноутбуки", "laptops", electronics);
		Category headphones = createCategory("Наушники", "headphones", electronics);


		createCategory("Apple", "apple-smartphones", smartphones);
		createCategory("Samsung", "samsung-smartphones", smartphones);
		createCategory("Xiaomi", "xiaomi-smartphones", smartphones);


		createCategory("Игровые", "gaming-laptops", laptops);
		createCategory("Ультрабуки", "ultrabooks", laptops);
		createCategory("Рабочие станции", "workstation-laptops", laptops);


		Category mensClothing = createCategory("Мужская", "mens-clothing", clothing);
		Category womensClothing = createCategory("Женская", "womens-clothing", clothing);


		createCategory("Футболки", "mens-t-shirts", mensClothing);
		createCategory("Джинсы", "mens-jeans", mensClothing);
		createCategory("Куртки", "mens-jackets", mensClothing);


		createCategory("Платья", "womens-dresses", womensClothing);
		createCategory("Юбки", "womens-skirts", womensClothing);
		createCategory("Блузки", "womens-blouses", womensClothing);


		createCategory("Художественная литература", "fiction-books", books);
		createCategory("Научная литература", "science-books", books);
		createCategory("Детские книги", "children-books", books);

		log.info("Инициализация категорий завершена. Создано {} категорий", categoryRepository.count());
	}

	private Category createCategory(String name, String slug, Category parent) {
		Category category = Category.builder()
				.name(name)
				.slug(slug)
				.parent(parent)
				.build();

		Category saved = categoryRepository.save(category);
		log.debug("Создана категория: {} (slug: {}) parent: {}",
				name, slug, parent != null ? parent.getName() : "root");

		return saved;
	}
}