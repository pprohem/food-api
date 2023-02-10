package com.foodcommerce.foodapi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.foodcommerce.foodapi.Enums.EStatus;
import com.foodcommerce.foodapi.dto.Products.ProductIdDTO;
import com.foodcommerce.foodapi.dto.Purchase.PurchaseProductRequestDTO;
import com.foodcommerce.foodapi.dto.Purchase.UserPurchaseRequestDTO;
import com.foodcommerce.foodapi.dto.Purchase.UserPurchaseResponseDTO;
import com.foodcommerce.foodapi.exception.ProductException;
import com.foodcommerce.foodapi.exception.UserException;
import com.foodcommerce.foodapi.exception.UserPurchaseException;
import com.foodcommerce.foodapi.model.Product;
import com.foodcommerce.foodapi.model.Purchase;
import com.foodcommerce.foodapi.model.User;
import com.foodcommerce.foodapi.model.UserPurchase;
import com.foodcommerce.foodapi.repository.ProductRepository;
import com.foodcommerce.foodapi.repository.PurchaseRepository;
import com.foodcommerce.foodapi.repository.UserPurchaseRepository;
import com.foodcommerce.foodapi.repository.UserRepository;
import com.foodcommerce.foodapi.service.UserServices.UserDetailsImplements;

import jakarta.transaction.Transactional;

@Service
public class UserPurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserPurchaseRepository userPurchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<UserPurchaseResponseDTO> findAllUserPurchases() {
        return userPurchaseRepository.findAll().stream()
                .map(UserPurchaseResponseDTO::new).collect(Collectors.toList());
    }

    public UserPurchaseResponseDTO findUserPurchaseById(Long id) {
        return userPurchaseRepository.findById(id).map(UserPurchaseResponseDTO::new)
                .orElseThrow(() -> new UserPurchaseException("Could not find userPurchase id = " + id));
    }

    @Transactional
    public UserPurchaseResponseDTO insertUserPurchase(UserPurchaseRequestDTO request) {

        UserDetailsImplements userDetails = (UserDetailsImplements) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UserException("Could not find user id = " + userDetails.getId()));

        UserPurchase userPurchase = new UserPurchase();
        userPurchase.setUser(user);
        userPurchase.setPurchaseStatus(EStatus.PENDENTE);
        userPurchase.setPurchaseDate(LocalDateTime.now());
        Double totalPrice = 0.0;
        for (PurchaseProductRequestDTO purchase : request.getPurchases()) {

            Product product = productRepository.findById(purchase.getProduct().getId())
                .orElseThrow(() -> new ProductException("Could not find product, id = " + purchase.getProduct().getId()));

            purchase.setProduct(
                productRepository.findById(purchase.getProduct().getId()).map(ProductIdDTO::new).orElseThrow(
                    () -> new ProductException("Product not found with id " + purchase.getProduct().getId())));
                    totalPrice += product.getPrice() * purchase.getQuantity();
                }
        userPurchase.setTotalPrice(totalPrice);
        userPurchase = userPurchaseRepository.save(userPurchase);


        List<Purchase> purchaseTransfer = new ArrayList<>();
        request.getPurchases().stream().forEach(purchaseProductRequestDTO -> 
            purchaseTransfer.add(purchaseTransferObject(purchaseProductRequestDTO)));

        for (Purchase p : purchaseTransfer){
            p.setUserPurchase(userPurchase);
            purchaseRepository.save(p);
        }

        return new UserPurchaseResponseDTO(userPurchase);
    }

    private Purchase purchaseTransferObject(PurchaseProductRequestDTO purchaseDTO) {
        Purchase purchase = new Purchase();

        Product product = productRepository.findById(purchaseDTO.getProduct().getId())
                .orElseThrow(() -> new ProductException("Could not find product, id = " + purchaseDTO.getProduct().getId()));

        purchase.setProduct(product);
        purchase.setUnitPrice(product.getPrice());
        purchase.setQuantity(purchaseDTO.getQuantity());

        return purchase;
    }
}