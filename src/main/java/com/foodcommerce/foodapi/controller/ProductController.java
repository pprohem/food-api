package com.foodcommerce.foodapi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.foodcommerce.foodapi.dto.Products.ProductRequestDTO;
import com.foodcommerce.foodapi.dto.Products.ProductResponseDTO;
import com.foodcommerce.foodapi.exception.ApiError;
import com.foodcommerce.foodapi.exception.ProductException;
import com.foodcommerce.foodapi.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    ProductService productService;


    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() { 
        return ResponseEntity.ok(productService.findAllProducts());
    }


    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){

        try {
            return ResponseEntity.ok(productService.findProductById(id));
        } catch (ProductException ex) {
            return ResponseEntity.unprocessableEntity()
                .body(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity", ex.getLocalizedMessage()));
        }
    }


    @PostMapping
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductRequestDTO productRequest) {
        try {
         ProductResponseDTO response = productService.insertProduct(productRequest);
          URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
              .buildAndExpand(response.getId())
              .toUri();
          return ResponseEntity.created(uri).body(response);
        } catch (ProductException e) {
          return ResponseEntity.unprocessableEntity()
              .body(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity", e.getLocalizedMessage()));
        }
      };

      @PutMapping("{id}")
      public ResponseEntity<Object> updateCategory(@PathVariable Long id, @Valid @RequestBody ProductResponseDTO productRequest) {
        try {
            ProductResponseDTO response = productService.updateProduct(id, productRequest);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

            return ResponseEntity.created(uri).body(response);

        } catch (ProductException e) {
            return ResponseEntity.unprocessableEntity()
                .body(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity", e.getLocalizedMessage()));
          }
        };

        @DeleteMapping("{id}")
        public ResponseEntity<Object> deleteCategory(@PathVariable Long id) { 
            try {
                productService.deleteProduct(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }catch (ProductException | DataIntegrityViolationException e) {
                return ResponseEntity.unprocessableEntity()
                    .body(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity", e.getLocalizedMessage()));
              }
        }

}
