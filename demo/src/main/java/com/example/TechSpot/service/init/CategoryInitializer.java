package com.example.TechSpot.service.init;

import com.example.TechSpot.entity.Category;
import com.example.TechSpot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Log4j2
public class CategoryInitializer {

	private final CategoryRepository categoryRepository;

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void initCategories() {
		if (categoryRepository.count() > 0) {
			log.info("Категории уже существуют, пропускаем инициализацию");
			return;
		}

		log.info("Начало инициализации категорий...");

		// Создаем корневые категории
		Category electronics = createCategory("Электроника", "electronics", null);
		Category clothing = createCategory("Одежда", "clothing", null);
		Category books = createCategory("Книги", "books", null);

		// Подкатегории для Электроники
		Category smartphones = createCategory("Смартфоны", "smartphones", electronics);
		Category laptops = createCategory("Ноутбуки", "laptops", electronics);
		Category headphones = createCategory("Наушники", "headphones", electronics);

		// Подкатегории для Смартфонов
		createCategory("Apple", "apple-smartphones", smartphones);
		createCategory("Samsung", "samsung-smartphones", smartphones);
		createCategory("Xiaomi", "xiaomi-smartphones", smartphones);

		// Подкатегории для Ноутбуков
		createCategory("Игровые", "gaming-laptops", laptops);
		createCategory("Ультрабуки", "ultrabooks", laptops);
		createCategory("Рабочие станции", "workstation-laptops", laptops);

		// Подкатегории для Одежды
		Category mensClothing = createCategory("Мужская", "mens-clothing", clothing);
		Category womensClothing = createCategory("Женская", "womens-clothing", clothing);

		// Подкатегории для Мужской одежды
		createCategory("Футболки", "mens-t-shirts", mensClothing);
		createCategory("Джинсы", "mens-jeans", mensClothing);
		createCategory("Куртки", "mens-jackets", mensClothing);

		// Подкатегории для Женской одежды
		createCategory("Платья", "womens-dresses", womensClothing);
		createCategory("Юбки", "womens-skirts", womensClothing);
		createCategory("Блузки", "womens-blouses", womensClothing);

		// Подкатегории для Книг
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