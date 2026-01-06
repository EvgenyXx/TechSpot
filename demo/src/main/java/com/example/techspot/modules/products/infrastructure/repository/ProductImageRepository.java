package com.example.techspot.modules.products.infrastructure.repository;

import com.example.techspot.modules.products.domain.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

	List<ProductImage> findByProductId(Long productId);

	long countByProductId(Long productId);
}
