package com.foodcommerce.foodapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodcommerce.foodapi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByNameIgnoreCase(String name);
}
