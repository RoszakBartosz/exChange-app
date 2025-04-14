package com.example.exchange_app.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthenticationTokenRequest {
    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private String refreshToken;
}
