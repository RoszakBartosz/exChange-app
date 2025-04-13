package com.example.exchange_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExChangeRateRequest {
    private String currency;
    private String code;
    private BigDecimal fromMid;
    private BigDecimal toMid;
}

//
