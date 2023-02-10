package com.foodcommerce.foodapi.dto.Purchase;

import com.foodcommerce.foodapi.dto.Products.ProductResponseDTO;
import com.foodcommerce.foodapi.model.Purchase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseResponseDTO {
    
    private int quantity; 
    private double unitPrice; 
    private ProductResponseDTO product ;


    public PurchaseResponseDTO(Purchase purchase) {
        this.quantity = purchase.getQuantity();
        this.unitPrice = purchase.getUnitPrice();
        this.product = new ProductResponseDTO(purchase.getProduct());
    }
}
