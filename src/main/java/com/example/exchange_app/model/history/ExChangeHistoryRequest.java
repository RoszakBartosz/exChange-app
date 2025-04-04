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
    private BigDecimal midInTheseTimeFrom;
    private BigDecimal midInTheseTimeTo;
    private String chosenCurrencyFrom;
    private String chosenCurrencyTo;
    private LocalDateTime FromDateTimeFromOperation;
    private LocalDateTime ToDateTimeFromOperation;
}
