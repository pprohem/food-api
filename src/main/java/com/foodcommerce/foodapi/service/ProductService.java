package com.foodcommerce.foodapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foodcommerce.foodapi.dto.Products.ProductRequestDTO;
import com.foodcommerce.foodapi.dto.Products.ProductResponseDTO;
import com.foodcommerce.foodapi.exception.CategoryException;
import com.foodcommerce.foodapi.exception.ProductException;
import com.foodcommerce.foodapi.model.Category;
import com.foodcommerce.foodapi.model.Product;
import com.foodcommerce.foodapi.repository.CategoryRepository;
import com.foodcommerce.foodapi.repository.ProductRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<ProductResponseDTO> findAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDTO::new).collect(Collectors.toList());
    }



    public Page <ProductResponseDTO> searchProducts(String name , Boolean isActive, Pageable pageable) {
        Page<Product> products = productRepository.findByNameContainingIgnoreCaseAndIsActive(name, isActive, pageable);
        return products.map(ProductResponseDTO::new);
    }



    public ProductResponseDTO findProductById(Long id) {
        return productRepository.findById(id).map(ProductResponseDTO::new)
                .orElseThrow(() -> new ProductException("Could not find product with id " + id));
    }

    @Transactional
    public ProductResponseDTO insertProduct(ProductRequestDTO product) {
        if (productRepository.existsByNameIgnoreCase(product.getName())) {
            throw new ProductException("The category already exists");
        }

        Category cat = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new CategoryException("Could not find category id"
                        + product.getCategory().getId()));

        Product p = new Product();
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setQuantity(product.getQuantity());
        p.setImageUrl(product.getImageUrl());
        p.setIsActive(true);
        p.setCategory(cat);

        p = productRepository.save(p);

        return new ProductResponseDTO(p);
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, @Valid ProductResponseDTO productRequest) {

        Product p = productRepository.findById(id).orElseThrow(() -> new ProductException("Could not find product id"
                + id));

        String name = productRequest.getName();

        if (!p.getName().equalsIgnoreCase(name) && productRepository.existsByNameIgnoreCase(name)) {
            throw new ProductException("Name already exists for category name = " + name);
        }

        Category cat = categoryRepository.findById(productRequest.getCategory().getId())
                .orElseThrow(() -> new CategoryException("Could not find category id"
                        + productRequest.getCategory().getId()));

        p.setName(name);
        p.setDescription(productRequest.getDescription());
        p.setPrice(productRequest.getPrice());
        p.setQuantity(productRequest.getQuantity());
        p.setImageUrl(productRequest.getImageUrl());
        p.setIsActive(true);
        p.setCategory(cat);

        p = productRepository.save(p);
        return new ProductResponseDTO(p);
    }

    @Transactional

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Could not find product id=" + id));

        product.setIsActive(false);
        productRepository.save(product);
    }

}
