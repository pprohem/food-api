package com.foodcommerce.foodapi.dto.Products;

import com.foodcommerce.foodapi.model.Category;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CategoryToProductRequestDTO {
    @NotNull
    private Long id; 

    public CategoryToProductRequestDTO(Category c) {
        this.id = c.getId();
    }
}
