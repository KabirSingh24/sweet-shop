package com.sweet_shop_backend.backend.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
