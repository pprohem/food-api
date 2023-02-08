package com.foodcommerce.foodapi.dto.Products;
import com.foodcommerce.foodapi.model.Product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

public class ProductRequestDTO {
    
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    private Double price;


    @NotNull(message = "Quantity is required")
    private int quantity; 

    private String imageUrl; 

    private Boolean  isActive; 

    @NotNull
    @Valid
    private CategoryToProductRequestDTO category;

    
    
    public ProductRequestDTO(Product p){
        this.name = p.getName();
        this.description = p.getDescription();
        this.price = p.getPrice();
        this.imageUrl = p.getImageUrl();
        this.quantity = p.getQuantity();
        this.isActive = p.getIsActive();
        this.category = new CategoryToProductRequestDTO(p.getCategory());
    }
}
