package com.example.TechSpot.repository;

import com.example.TechSpot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<User, UUID> {

	boolean existsByPhoneNumber(String phoneNumber);

	boolean existsByEmail(String email);

	Optional<User>findByEmail(String email);
}
