package com.foodcommerce.foodapi.dto.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequestDTO {
    @NotBlank
    private String refreshToken;

}
