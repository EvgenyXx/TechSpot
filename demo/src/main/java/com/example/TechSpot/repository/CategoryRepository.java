package com.example.TechSpot.repository;

import com.example.TechSpot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

	List<Category>findByParentIsNull();

	boolean existsByParent(Category parent);

	List<Category>findByParent(Category parent);

	boolean existsByNameAndParent(String name,Category parent);

	boolean existsBySlugAndParent(String slug,Category parent);

	Optional<Category>findBySlug(String slug);

	List<Category> findByNameContainingIgnoreCase(String name);


	@Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.parent.id = :parentId")
	boolean existsByParentId(@Param("parentId") Long parentId);

	default boolean isLeafCategory(Long categoryId) {
		return !existsByParentId(categoryId);
	}


}
