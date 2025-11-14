package com.example.TechSpot.repository;


import com.example.TechSpot.entity.CartItems;
import org.hibernate.cache.internal.QueryResultsCacheImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems,Long> {
}
