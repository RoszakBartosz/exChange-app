package com.example.exchange_app.model.history;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "exchangehistorylog")
public class ExChangeHistoryLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal fromAmountOperation;
    private BigDecimal ToAmountOperation;
    private BigDecimal midInTheseTimeFrom;
    private BigDecimal midInTheseTimeTo;
    private String chosenCurrencyFrom;
    private String chosenCurrencyTo;
    private LocalDateTime dateTimeFromOperation;
}
