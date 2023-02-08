package com.foodcommerce.foodapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodcommerce.foodapi.dto.Category.CategoryRequestDTO;
import com.foodcommerce.foodapi.dto.Category.CategoryResponseDTO;
import com.foodcommerce.foodapi.dto.Category.CategoryResponseListDTO;
import com.foodcommerce.foodapi.exception.CategoryException;
import com.foodcommerce.foodapi.model.Category;
import com.foodcommerce.foodapi.repository.CategoryRepository;

import jakarta.transaction.Transactional;


@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
  
    public List<CategoryResponseListDTO> findAllCategories() {
        return categoryRepository.findAll().stream()
        .map(CategoryResponseListDTO::new).collect(Collectors.toList());
    }

    public CategoryResponseListDTO findCategoryById(Long id) { 
        return categoryRepository.findById(id).map(CategoryResponseListDTO::new).orElseThrow(() 
        -> new CategoryException("Could not find catergory id= " + id)
            
        );
    }

    @Transactional
    public CategoryResponseDTO insertCategory(CategoryRequestDTO category) {
        if(categoryRepository.existsByNameIgnoreCase(category.getName())){
            throw new CategoryException("The category already exists");
        }

        Category cat = new Category();
        cat.setName(category.getName());
        cat.setDescription(category.getDescription());
        cat = categoryRepository.save(cat);

        return new CategoryResponseDTO(cat); 
    }

    
    @Transactional
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO category) {

        Category cat = categoryRepository.findById(id).orElseThrow(() -> new CategoryException("Could not find category id"
        + id));

        String name = category.getName();

        if(!cat.getName().equalsIgnoreCase(name) && categoryRepository.existsByNameIgnoreCase(name)) { 
            throw new CategoryException("Name already exists for category name = " + name);
        }

        cat.setName(name);
        cat.setDescription(category.getDescription());

        cat = categoryRepository.save(cat);
        return new CategoryResponseDTO(cat); 
    }
    
    
    @Transactional
    public void deleteCategory(Long id) { 
        Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new CategoryException("Could not find category id=" + id));

        if(hasProducts(category)) { 
            throw new CategoryException("Categor has products, cannot be deleted"); 
        }

        categoryRepository.deleteById(id);
    }

    private boolean hasProducts(Category category) {
        return category.getProducts().size() > 0;
    }


  

}
