package com.example.exchange_app.service;


import com.example.exchange_app.model.history.ExChangeHistoryLog;
import com.example.exchange_app.repository.ExChangingHistoryLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportGeneratorService {
    private final ExChangingHistoryLogRepository repository;

    public List<ExChangeHistoryLog> getHighAmountTransactionsFromLastMonth(){
        return repository.getHighAmountFromLastMonth();
    }
    public Map<String, List<BigDecimal>> getMonthFrom() {
        List<Object[]> fromLastMonthGroupedByCurrencyFrom = repository.getFromLastMonthGroupedByCurrencyFrom();
        Map<String, List<BigDecimal>> currencyAmountMap = fromLastMonthGroupedByCurrencyFrom.stream()
                .collect(Collectors.groupingBy(
                        obj -> (String) obj[0],
                        Collectors.mapping(
                                obj -> (BigDecimal) obj[1],
                                Collectors.toList())));
        return currencyAmountMap;
    }

    public Map<String, List<BigDecimal>> getMonthTo(){
        List<Object[]> fromLastMonthGroupedByCurrencyFrom = repository.getFromLastMonthGroupedByCurrencyTo();
        Map<String, List<BigDecimal>> currencyAmountMap = fromLastMonthGroupedByCurrencyFrom.stream()
                .collect(Collectors.groupingBy(
                        obj -> (String) obj[0],
                        Collectors.mapping(
                                obj -> (BigDecimal) obj[1],
                                Collectors.toList())));
        return currencyAmountMap;
    }
}

