package com.example.TechSpot.repository;

import com.example.TechSpot.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

	List<Order> findByUserId(UUID userId);
	List<Order> findByUserIdOrderByCreatedAtDesc(UUID userId);
}
