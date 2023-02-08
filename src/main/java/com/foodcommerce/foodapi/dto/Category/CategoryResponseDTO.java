package com.foodcommerce.foodapi.dto.Category;

import com.foodcommerce.foodapi.model.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryResponseDTO {
    
    private Long id; 
    private String name;
  
    private String description;

    public CategoryResponseDTO(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
    }
}
