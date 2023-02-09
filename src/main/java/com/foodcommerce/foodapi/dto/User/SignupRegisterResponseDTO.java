package com.foodcommerce.foodapi.dto.User;

import java.util.List;

import com.foodcommerce.foodapi.Enums.ERole;
import com.foodcommerce.foodapi.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRegisterResponseDTO {
    private Long id;
    private String username;
    private String email;
    private List<ERole> roles; 

    public SignupRegisterResponseDTO(User u, List<ERole> roles2) { 
        this.id = u.getId();
        this.username = u.getUsername();
        this.email = u.getEmail();
        this.roles = roles2;

    }
}
