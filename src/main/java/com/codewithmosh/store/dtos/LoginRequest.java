package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Id is required")
    private Long id;

    @NotBlank(message = "Password is required")
    private String password;
}
