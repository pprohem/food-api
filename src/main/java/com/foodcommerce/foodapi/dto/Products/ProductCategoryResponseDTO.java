package com.foodcommerce.foodapi.dto.Products;

import com.foodcommerce.foodapi.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductCategoryResponseDTO {
   
    private Long id;

    private String name;

    private String description;

    private Double price;

    private int quantity; 

    private String imageUrl; 

    private Boolean  isActive; 


    public ProductCategoryResponseDTO(Product p){
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.price = p.getPrice();
        this.isActive= p.getIsActive();
        this.quantity = p.getQuantity();
        this.imageUrl = p.getImageUrl();
    }
}
