package com.foodcommerce.foodapi.dto.Products;

import com.foodcommerce.foodapi.dto.Category.CategoryResponseDTO;
import com.foodcommerce.foodapi.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private int quantity;
    private Boolean isActive;
    private String imageUrl;
    private CategoryResponseDTO category;

    public ProductResponseDTO(Product p) {

        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.price = p.getPrice();
        this.quantity = p.getQuantity();
        this.isActive = p.getIsActive();
        this.imageUrl = p.getImageUrl();
        this.category = new CategoryResponseDTO(p.getCategory());

    }
}
