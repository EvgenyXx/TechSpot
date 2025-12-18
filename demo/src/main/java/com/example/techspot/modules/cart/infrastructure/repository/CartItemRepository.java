package com.example.techspot.modules.cart.infrastructure.repository;


import com.example.techspot.modules.cart.domain.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems,Long> {
}
