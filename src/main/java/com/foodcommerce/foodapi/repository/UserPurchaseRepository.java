package com.foodcommerce.foodapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodcommerce.foodapi.model.UserPurchase;

public interface UserPurchaseRepository extends JpaRepository<UserPurchase, Long>{
    
}
