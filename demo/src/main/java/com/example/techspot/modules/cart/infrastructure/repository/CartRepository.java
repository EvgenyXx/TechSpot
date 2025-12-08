package com.example.techspot.modules.cart.infrastructure.repository;


import com.example.techspot.modules.cart.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

	Optional<Cart>findByUserId(UUID userId);


	@Query(value = "select date (c.created_at),COUNT(*) from cart c " +
			"group by date(c.created_at)",nativeQuery = true)
	List<Object[]> getStat ();
}
