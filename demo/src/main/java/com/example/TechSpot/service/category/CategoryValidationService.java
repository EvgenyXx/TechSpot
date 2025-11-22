package com.example.TechSpot.service.category;

import com.example.TechSpot.entity.Category;
import com.example.TechSpot.exception.category.CategoryAlreadyExistsException;
import com.example.TechSpot.exception.review.SlugAlreadyExistsException;
import com.example.TechSpot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CategoryValidationService {

	private final CategoryRepository categoryRepository;

	public void validateUniqueNameForParent(String name, Category parent) {
		log.debug("Проверка уникальности имени: name='{}', parent={}",
				name, parent != null ? parent.getId() : "null");

		if (categoryRepository.existsByNameAndParent(name, parent)) {
			log.warn("Нарушение уникальности имени: name='{}' уже существует для parent={}",
					name, parent != null ? parent.getId() : "root");
			throw new CategoryAlreadyExistsException();
		}

		log.debug("Проверка имени пройдена: name='{}' уникально", name);
	}

	public void validateUniqueSlugForParent(String slug, Category parent) {
		log.debug("Проверка уникальности slug: slug='{}', parent={}",
				slug, parent != null ? parent.getId() : "null");

		if (categoryRepository.existsBySlugAndParent(slug, parent)) {
			log.warn("Нарушение уникальности slug: slug='{}' уже существует для parent={}",
					slug, parent != null ? parent.getId() : "root");
			throw new SlugAlreadyExistsException();
		}

		log.debug("Проверка slug пройдена: slug='{}' уникален", slug);
	}
}