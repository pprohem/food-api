package com.foodcommerce.foodapi.exception;

public class UserPurchaseException extends RuntimeException {
    private static final long serialVersionUID = 1L; 

    public UserPurchaseException(String message) {
        super(message);
    }
}
