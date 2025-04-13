package com.example.exchange_app.model.dto;

import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RequestExChangeDTO {
    @NotBlank(message = "codeCurrencyFrom cannot be empty! ")
    private String codeCurrencyFrom;
    @NotBlank(message = "codeCurrencyTo cannot be empty! ")
    private String codeCurrencyTo;
    @NotNull(message = "valueFrom cannot be null! ")
    @Positive(message = "valueFrom must be greater than 0 ")
    private BigDecimal valueFrom;
}
