package com.foodcommerce.foodapi.dto.Purchase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.foodcommerce.foodapi.Enums.EStatus;
import com.foodcommerce.foodapi.dto.User.UserResponseDTO;
import com.foodcommerce.foodapi.model.UserPurchase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class UserPurchaseResponseDTO {

    private Long id;
    private UserResponseDTO user;
    private double totalPrice;
    private EStatus purchaseStatus;
    private LocalDateTime purchaseDate;
    private List<PurchaseResponseDTO> purchases = new ArrayList<>();

    public UserPurchaseResponseDTO(UserPurchase userPurchase) {
        this.id = userPurchase.getId();
        this.user = new UserResponseDTO(userPurchase.getUser());
        this.totalPrice = userPurchase.getTotalPrice();
        this.purchaseStatus = userPurchase.getPurchaseStatus();
        this.purchaseDate = userPurchase.getPurchaseDate();
        if (userPurchase.getPurchases() != null) {
            userPurchase.getPurchases().forEach(purchase -> purchases.add(new PurchaseResponseDTO(purchase)));
        }
    }

}