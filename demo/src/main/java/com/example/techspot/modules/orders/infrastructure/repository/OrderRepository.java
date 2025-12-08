package com.example.techspot.modules.orders.infrastructure.repository;

import com.example.techspot.modules.orders.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

	List<Order> findByUserId(UUID userId);
	List<Order> findByUserIdOrderByCreatedAtDesc(UUID userId);

	Optional<Order> findByIdAndUserId(Long orderId, UUID userId);

	long countByUserId(UUID userId);

	@Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.user.id = :userId")
	Double sumTotalAmountByUserId(@Param("userId") UUID userId);

	@Query("SELECT COUNT(DISTINCT o.user.id) FROM Order o")
	Long countUsersWithAtLeastOneOrder();


}
