package com.example.exchange_app.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class ResponseRatesDTO implements Serializable {
    private String code;
    private String currency;
    private BigDecimal mid;
}
