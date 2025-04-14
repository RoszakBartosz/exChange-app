package com.example.exchange_app.model.dto;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class CalculatorResponseDTO {
    @Positive(message = "not positive value in calculatorResponseDTO value. ")
    private BigDecimal value;
    @Positive(message = "not positive value in mid calculatorResponseDTO. ")
    private BigDecimal mid;
}
