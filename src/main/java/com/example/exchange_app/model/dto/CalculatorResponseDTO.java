package com.example.exchange_app.model.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalculatorResponseDTO {
    @Positive(message = "not positive value in calculatorResponseDTO value. ")
    private BigDecimal value;
    @Positive(message = "not positive value in mid calculatorResponseDTO. ")
    private BigDecimal mid;
}
