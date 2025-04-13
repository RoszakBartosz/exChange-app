package com.example.exchange_app.model.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExChangeHistoryRequest {
    private long id;
    private BigDecimal fromAmountOperation;
    private BigDecimal ToAmountOperation;
    private BigDecimal midInTheseTimeFromFrom;
    private BigDecimal midInTheseTimeFromTo;
    private BigDecimal midInTheseTimeToFrom;
    private BigDecimal midInTheseTimeToTo;
    private String chosenCurrencyFrom;
    private String chosenCurrencyTo;
    private LocalDateTime FromDateTimeFromOperation;
    private LocalDateTime ToDateTimeFromOperation;
}
