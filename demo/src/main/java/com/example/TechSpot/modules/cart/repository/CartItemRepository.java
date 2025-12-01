package com.example.TechSpot.modules.cart.repository;


import com.example.TechSpot.modules.cart.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems,Long> {
}
