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
public class RegisterRequestDTO {

    @NotNull(message = "Firstname cannot be null")
    @NotBlank(message = "Firstname cannot be blank")
    private String firstname;

    @NotNull(message = "Lastname cannot be null")
    @NotBlank(message = "Lastname cannot be blank")
    private String lastname;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
