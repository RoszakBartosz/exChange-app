package com.example.exchange_app.repository;

import com.example.exchange_app.model.history.ExChangeHistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExChangingHistoryLogRepository extends JpaRepository<ExChangeHistoryLog, Long> {
}
