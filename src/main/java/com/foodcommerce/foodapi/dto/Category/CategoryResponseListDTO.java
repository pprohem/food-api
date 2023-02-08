package com.foodcommerce.foodapi.dto.Category;
import java.util.ArrayList;
import java.util.List;

import com.foodcommerce.foodapi.dto.Products.ProductCategoryResponseDTO;
import com.foodcommerce.foodapi.model.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryResponseListDTO {
    
    private Long id; 
    private String name;
    private String description;
    private List<ProductCategoryResponseDTO> products = new ArrayList<>();

    public CategoryResponseListDTO(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();

        if(category.getProducts() != null) {
            category.getProducts().forEach(product -> products.add(new ProductCategoryResponseDTO(product)));
        }
    }
}
