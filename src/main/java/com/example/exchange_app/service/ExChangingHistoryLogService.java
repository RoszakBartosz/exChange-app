package com.example.exchange_app.service;

import com.example.exchange_app.model.ExChangeRateRequest;
import com.example.exchange_app.model.QExChangeRate;
import com.example.exchange_app.model.history.ExChangeHistoryRequest;
import com.example.exchange_app.model.history.QExChangeHistoryLog;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import com.example.exchange_app.model.history.ExChangeHistoryLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.exchange_app.repository.ExChangingHistoryLogRepository;

import java.util.Objects;

@RequiredArgsConstructor
@Service
@Table(name = "exchangehistorylog")
public class ExChangingHistoryLogService {
    private final ExChangingHistoryLogRepository repository;
    QExChangeHistoryLog exChangeHistoryLog = QExChangeHistoryLog.exChangeHistoryLog;

//    BooleanExpression predicate1 = buildPredicate(ExChangeHistoryRequest, exChangeHistoryLog);
//    public void saveHistory(ExChangeHistoryLog exChangingHistoryLog){
//        repository.save(exChangingHistoryLog);
//    }
//    //  todo queryDSL
//    public Page<ExChangeHistoryLog> findAll(Pageable pageable){
//    Page<ExChangeHistoryLog> all = repository.findAll(predicate1, pageable);
//        return all;
//}
//
//private BooleanExpression buildPredicate(ExChangeHistoryRequest Request, QExChangeRate exChangeHistoryLog) {
//    BooleanExpression predicate = exChangeRate.isNotNull();
//
//    String currency = exChangeRateRequest.getCode();
//
//    if (Objects.nonNull(currency) && !currency.isEmpty()) {
//        predicate = predicate.and(exChangeRate.code.equalsIgnoreCase(currency));
//    }
//
//    if (Objects.nonNull(currency) && !currency.isEmpty()) {
//        predicate = predicate.and(exChangeRate.mid.loe(exChangeRateRequest.getToMid()));
//    }
//
//
//    if (Objects.nonNull(currency) && !currency.isEmpty()) {
//        predicate = predicate.and(exChangeRate.mid.goe(exChangeRateRequest.getFromMid()));
//    }
//    return predicate;
//
//}



}
