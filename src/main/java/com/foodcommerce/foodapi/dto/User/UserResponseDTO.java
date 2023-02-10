package com.foodcommerce.foodapi.dto.User;

import com.foodcommerce.foodapi.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;


    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();

    }
}
