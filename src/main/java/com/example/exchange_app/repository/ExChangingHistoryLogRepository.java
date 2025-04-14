package com.example.exchange_app.repository;

import com.example.exchange_app.model.history.ExChangeHistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Map;

public interface ExChangingHistoryLogRepository extends JpaRepository<ExChangeHistoryLog, Long>, QuerydslPredicateExecutor<ExChangeHistoryLog> {

    @Query(value = "SELECT * FROM exchangehistorylog WHERE date_time_from_operation > now() - interval '30 day' AND from_amount_operation > 15000", nativeQuery = true)
    List<ExChangeHistoryLog> getHighAmountFromLastMonth();

    @Query(value = """
    SELECT chosen_currency_from, SUM(from_amount_operation)
    FROM exchangehistorylog
    WHERE date_time_from_operation > now() - interval '30 days'
    GROUP BY chosen_currency_from
""", nativeQuery = true)    List<Object[]> getFromLastMonthGroupedByCurrencyFrom();

    @Query(value = """
    SELECT chosen_currency_to, SUM(from_amount_operation)
    FROM exchangehistorylog
    WHERE date_time_from_operation > now() - interval '30 days'
    GROUP BY chosen_currency_to
""", nativeQuery = true)    List<Object[]> getFromLastMonthGroupedByCurrencyTo();
}
