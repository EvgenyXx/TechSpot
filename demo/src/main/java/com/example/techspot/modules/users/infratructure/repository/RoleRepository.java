package com.example.techspot.modules.users.infratructure.repository;


import com.example.techspot.modules.users.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

	Optional<Role>findByName(String roleName);



}
