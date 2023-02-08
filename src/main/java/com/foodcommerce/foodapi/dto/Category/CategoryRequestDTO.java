package com.foodcommerce.foodapi.dto.Category;

import com.foodcommerce.foodapi.model.Category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryRequestDTO {
    
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;

    public CategoryRequestDTO(Category category){
        this.name = category.getName();
        this.description = category.getDescription();
    }
}