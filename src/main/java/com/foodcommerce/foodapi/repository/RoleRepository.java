package com.foodcommerce.foodapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodcommerce.foodapi.Enums.ERole;
import com.foodcommerce.foodapi.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
