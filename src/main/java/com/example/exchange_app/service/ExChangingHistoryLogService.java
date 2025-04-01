package com.example.exchange_app.service;

import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import com.example.exchange_app.model.history.ExChangeHistoryLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.exchange_app.repository.ExChangingHistoryLogRepository;

@RequiredArgsConstructor
@Service
@Table(name = "exchangehistorylog")
public class ExChangingHistoryLogService {
    private final ExChangingHistoryLogRepository repository;

    public void saveHistory(ExChangeHistoryLog exChangingHistoryLog){
        repository.save(exChangingHistoryLog);
    }

    public Page<ExChangeHistoryLog> findAll(Pageable pageable){
        Page<ExChangeHistoryLog> all = repository.findAll(pageable);
        return all;
    }

}
