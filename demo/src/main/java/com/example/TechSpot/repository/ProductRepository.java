package com.example.TechSpot.repository;

import com.example.TechSpot.entity.Product;

import com.example.TechSpot.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {



	@Query("SELECT p FROM Product p WHERE " +
			"LOWER(p.productName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
			"LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%'))")
	Page<Product> searchProduct(
			String query,String description, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.category.slug = :slug")
	Page<Product> findByCategorySlug(@Param("slug") String slug, Pageable pageable);

	List<Product> findByUserId(UUID userId);

	List<Product> findTopByCategoryOrderByPriceDesc(ProductCategory category, Pageable pageable);

	@Modifying
	@Query("UPDATE Product p SET p.quantity = p.quantity - :quantity " +
			"WHERE p.id = :productId AND p.quantity >= :quantity")
	int reduceQuantity(@Param("productId") Long productId,
					   @Param("quantity") int quantity);


	@Query(
			value = """
					select p.product_name ,p.price\s
					from  products p\s
					where p.price  between :minPrice and  :maxPrice
					group by p.id ,p.product_name\s
					order by  p.price desc
					""",nativeQuery = true
	)
	List<Object[]>findProductByPriceRange(@Param("minPrice")BigDecimal minPrice,@Param("maxPrice")BigDecimal maxPrice);


	@Query(value = """
    SELECT p.name, p.price
    FROM products p
    WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
    GROUP BY p.id, p.name
    ORDER BY p.price DESC
    """, nativeQuery = true)
	List<Object[]> findProductsBySearchTerm(@Param("searchTerm") String searchTerm);


	boolean existsByProductNameAndUserId(String productName,UUID userId);
}
