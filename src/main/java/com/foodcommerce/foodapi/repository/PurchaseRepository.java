package com.foodcommerce.foodapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodcommerce.foodapi.model.Purchase;

public interface PurchaseRepository extends JpaRepository <Purchase, Long> {
    
}
