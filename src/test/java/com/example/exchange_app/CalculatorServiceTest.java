package com.example.exchange_app;

import com.example.exchange_app.model.ExChangeRate;
import com.example.exchange_app.model.dto.CalculatorResponseDTO;
import com.example.exchange_app.model.history.ExChangeHistoryLog;
import com.example.exchange_app.repository.ExChangingRepository;
import com.example.exchange_app.service.CalculatorService;
import com.example.exchange_app.service.ExChangingHistoryLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceTest {

    @Mock
    private ExChangingRepository repository;

    @Mock
    private ExChangingHistoryLogService historyLogService;

    @InjectMocks
    private CalculatorService calculatorService;

    @Test
    void shouldExchangeFromEURtoUSD() {
        // given
        String codeCurrencyFrom = "EUR";
        String codeCurrencyTo = "USD";
        BigDecimal valueFrom = BigDecimal.valueOf(100);
        BigDecimal midFrom = BigDecimal.valueOf(4.50);
        BigDecimal midTo = BigDecimal.valueOf(4.00);

        ExChangeRate eurRate = new ExChangeRate();
        eurRate.setCode(codeCurrencyFrom);
        eurRate.setMid(midFrom);

        ExChangeRate usdRate = new ExChangeRate();
        usdRate.setCode(codeCurrencyTo);
        usdRate.setMid(midTo);

        when(repository.findByCode(codeCurrencyFrom)).thenReturn(Optional.of(eurRate));
        when(repository.findByCode(codeCurrencyTo)).thenReturn(Optional.of(usdRate));

        // when
        CalculatorResponseDTO result = calculatorService.ExChange(codeCurrencyFrom, codeCurrencyTo, valueFrom);

        // then
        BigDecimal expected = valueFrom.multiply(midFrom).divide(midTo, 2, RoundingMode.HALF_UP);
        assertEquals(expected, result.getValue());
        assertEquals(midTo, result.getMid());

        verify(historyLogService).saveHistory(any(ExChangeHistoryLog.class));
    }
    @Test
    void shouldExchangeFromPLNtoUSD() {
        // given
        String codeCurrencyFrom = "PLN";
        String codeCurrencyTo = "USD";
        BigDecimal valueFrom = BigDecimal.valueOf(200);
        BigDecimal midTo = BigDecimal.valueOf(4.00);

        ExChangeRate usdRate = new ExChangeRate();
        usdRate.setCode(codeCurrencyTo);
        usdRate.setMid(midTo);

        when(repository.findByCode(codeCurrencyTo)).thenReturn(Optional.of(usdRate));

        // when
        CalculatorResponseDTO result = calculatorService.ExChange(codeCurrencyFrom, codeCurrencyTo, valueFrom);

        // then
        BigDecimal expected = valueFrom.divide(midTo, 2, RoundingMode.HALF_UP);
        assertEquals(expected, result.getValue());
        assertEquals(midTo, result.getMid());

        verify(historyLogService).saveHistory(any(ExChangeHistoryLog.class));
    }
}