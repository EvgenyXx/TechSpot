package com.example.TechSpot.modules.discount;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository  extends JpaRepository<Discount,Long> {
}

