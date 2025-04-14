package com.example.exchange_app.model.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequestDTO {
    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private String password;
    private String refreshToken;
}
