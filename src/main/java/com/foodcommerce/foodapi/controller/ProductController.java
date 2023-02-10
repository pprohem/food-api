package com.foodcommerce.foodapi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.foodcommerce.foodapi.dto.Products.ProductRequestDTO;
import com.foodcommerce.foodapi.dto.Products.ProductResponseDTO;
import com.foodcommerce.foodapi.exception.ApiError;
import com.foodcommerce.foodapi.exception.ProductException;
import com.foodcommerce.foodapi.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Actions For Food Commerce Products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    @Operation(summary = "Get All Products", description = "Get All", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully Get All Products!"
       ),
        @ApiResponse(responseCode = "400", ref = "BadRequest"),
        @ApiResponse(responseCode = "401", ref = "badcredentials"),
        @ApiResponse(responseCode = "422", ref = "unprocessableEntity"),
        @ApiResponse(responseCode = "500", ref = "internalServerError")
})
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @GetMapping("{id}")
    @Operation(summary = "Get Product By Id", description = "Get a Product By Id!", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully Get a Product By Id!"
       ),
        @ApiResponse(responseCode = "400", ref = "BadRequest"),
        @ApiResponse(responseCode = "401", ref = "badcredentials"),
        @ApiResponse(responseCode = "422", ref = "unprocessableEntity"),
        @ApiResponse(responseCode = "500", ref = "internalServerError")
})
    
    public ResponseEntity<Object> findById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(productService.findProductById(id));
        } catch (ProductException ex) {
            return ResponseEntity.unprocessableEntity()
                    .body(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity",
                            ex.getLocalizedMessage()));
        }
    }

    @GetMapping("search")
    @Operation(summary = "Get Products By Search Parameters", description = "Get Products By Search Parameters", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully Get Products!"
       ),
        @ApiResponse(responseCode = "400", ref = "BadRequest"),
        @ApiResponse(responseCode = "401", ref = "badcredentials"),
        @ApiResponse(responseCode = "422", ref = "unprocessableEntity"),
        @ApiResponse(responseCode = "500", ref = "internalServerError")
})
    public ResponseEntity<Page<ProductResponseDTO>> findProductByName(@RequestParam(required = false) String name,
            Pageable pageable,
            @RequestParam(required = true) Boolean isActive) {
        return ResponseEntity.ok(productService.searchProducts(name, isActive, pageable));
    }

    @PostMapping
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Post Product", description = "Post a Product", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully Posted Product!", content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = ProductResponseDTO.class))),
        @ApiResponse(responseCode = "400", ref = "BadRequest"),
        @ApiResponse(responseCode = "401", ref = "badcredentials"),
        @ApiResponse(responseCode = "422", ref = "unprocessableEntity"),
        @ApiResponse(responseCode = "500", ref = "internalServerError")
})
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductRequestDTO productRequest) {
        try {
            ProductResponseDTO response = productService.insertProduct(productRequest);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(response.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(response);
        } catch (ProductException e) {
            return ResponseEntity.unprocessableEntity()
                    .body(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity",
                            e.getLocalizedMessage()));
        }
    };

    @PutMapping("{id}")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update Product", description = "Update a Product", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully Update Product!", content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = ProductResponseDTO.class))),
        @ApiResponse(responseCode = "400", ref = "BadRequest"),
        @ApiResponse(responseCode = "401", ref = "badcredentials"),
        @ApiResponse(responseCode = "422", ref = "unprocessableEntity"),
        @ApiResponse(responseCode = "500", ref = "internalServerError")
})
    public ResponseEntity<Object> updateCategory(@PathVariable Long id,
            @Valid @RequestBody ProductResponseDTO productRequest) {
        try {
            ProductResponseDTO response = productService.updateProduct(id, productRequest);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(response.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(response);

        } catch (ProductException e) {
            return ResponseEntity.unprocessableEntity()
                    .body(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity",
                            e.getLocalizedMessage()));
        }
    };

    @DeleteMapping("{id}")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Post Product", description = "Post a Product", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully Posted Product!" 
        ),
        @ApiResponse(responseCode = "400", ref = "BadRequest"),
        @ApiResponse(responseCode = "401", ref = "badcredentials"),
        @ApiResponse(responseCode = "422", ref = "unprocessableEntity"),
        @ApiResponse(responseCode = "500", ref = "internalServerError")
})
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ProductException | DataIntegrityViolationException e) {
            return ResponseEntity.unprocessableEntity()
                    .body(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity",
                            e.getLocalizedMessage()));
        }
    }

}
