package com.foodcommerce.foodapi.exception;

public class PurchaseException extends RuntimeException{
    private static final long serialVersionUID = 1L; 

    public PurchaseException(String message) {
        super(message);
    }
}
