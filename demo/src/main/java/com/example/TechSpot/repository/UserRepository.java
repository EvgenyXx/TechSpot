package com.example.TechSpot.repository;

import com.example.TechSpot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	boolean existsByPhoneNumber(String phoneNumber);
	boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
	void deleteByEmail(String mail);

	@Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :date")
	long countUsersCreatedAfter(@Param("date") LocalDateTime date);

	long countByIsActiveTrue();
	long countByIsActiveFalse();

	long countByCreatedAtAfter(LocalDateTime dateTime);


	@Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true AND u.createdAt >= :startDate")
	long countActiveUsersAfter(@Param("startDate") LocalDateTime startDate);

	@Query("SELECT r.name, COUNT(u) FROM User u JOIN u.roles r GROUP BY r.name")
	List<Object[]> countUsersByRole();


	@Query("SELECT FUNCTION('DATE', u.createdAt), COUNT(u) FROM User u " +
			"WHERE u.createdAt >= :startDate " +
			"GROUP BY FUNCTION('DATE', u.createdAt) " +
			"ORDER BY FUNCTION('DATE', u.createdAt)")
	List<Object[]> countRegistrationsByDay(@Param("startDate") LocalDateTime startDate);


	@Modifying
	@Query(value = "UPDATE users SET password_reset_code = NULL, reset_code_expiry = NULL WHERE reset_code_expiry < :dateNow", nativeQuery = true)
	void clearExpiredResetCodes(@Param("dateNow") LocalDateTime dateNow);
}