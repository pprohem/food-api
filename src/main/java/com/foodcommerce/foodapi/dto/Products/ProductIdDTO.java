package com.foodcommerce.foodapi.dto.Products;

import com.foodcommerce.foodapi.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductIdDTO {

    private Long id;

    public ProductIdDTO(Product product) {
        this.id = product.getId();
    }
}