package com.example.exchange_app.repository;

import com.example.exchange_app.model.history.ExChangeHistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ExChangingHistoryLogRepository extends JpaRepository<ExChangeHistoryLog, Long>, QuerydslPredicateExecutor<ExChangeHistoryLog> {
}
