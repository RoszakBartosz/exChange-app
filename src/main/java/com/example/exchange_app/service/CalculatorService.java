package com.example.exchange_app.service;

import com.example.exchange_app.model.dto.CalculatorResponseDTO;
import lombok.RequiredArgsConstructor;
import com.example.exchange_app.model.ExChangeRate;
import com.example.exchange_app.model.history.ExChangeHistoryLog;
import org.springframework.stereotype.Service;
import com.example.exchange_app.repository.ExChangingRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CalculatorService {
    private final ExChangingRepository repository;
    private final ExChangingHistoryLogService historyLogService;

    public CalculatorResponseDTO ExChange(String codeCurrencyFrom, String codeCurrencyTo, BigDecimal valueFrom){
        BigDecimal midTo = repository.findByCode(codeCurrencyTo).orElseThrow(() -> new NullPointerException("the codeCurrencyTo is null ")).getMid();


        if (!codeCurrencyFrom.equals("PLN")) {
            ExChangeRate currencyFrom = repository.findByCode(codeCurrencyFrom).orElseThrow(() -> new NullPointerException("the codeCurrencyFrom is null"));
            BigDecimal midFrom = currencyFrom.getMid();
            BigDecimal valueFromInPLN = valueFrom.multiply(midFrom);
            BigDecimal value = valueFromInPLN.divide(midTo, 2, RoundingMode.HALF_UP);


            ExChangeHistoryLog exChangeHistoryLog = ExChangeHistoryLog.builder()
                    .chosenCurrencyFrom(codeCurrencyFrom)
                    .fromAmountOperation(valueFrom)
                    .chosenCurrencyTo(codeCurrencyTo)
                    .ToAmountOperation(value)
                    .midInTheseTimeFrom(midFrom)
                    .midInTheseTimeTo(midTo)
                    .dateTimeFromOperation(LocalDateTime.now())
                    .build();

            historyLogService.saveHistory(exChangeHistoryLog);

            return CalculatorResponseDTO.builder()
                    .value(value)
                    .mid(midTo)
                    .build();
        } else {
            BigDecimal value = valueFrom;
            BigDecimal resultFromDivide = value.divide(midTo, 2, RoundingMode.HALF_UP);
            ExChangeHistoryLog exChangeHistoryLog = ExChangeHistoryLog.builder()
                    .chosenCurrencyFrom(codeCurrencyFrom)
                    .fromAmountOperation(valueFrom)
                    .chosenCurrencyTo(codeCurrencyTo)
                    .ToAmountOperation(value)
                    .midInTheseTimeFrom(BigDecimal.valueOf(1.00))
                    .midInTheseTimeTo(midTo)
                    .dateTimeFromOperation(LocalDateTime.now())
                    .build();

            historyLogService.saveHistory(exChangeHistoryLog);

            return  CalculatorResponseDTO.builder()
                    .value(resultFromDivide)
                    .mid(midTo)
                    .build();
        }


    }

}
