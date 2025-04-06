package com.example.exchange_app.repository;

import com.example.exchange_app.model.ExChangeRate;
import com.example.exchange_app.service.CalculatorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ExChangingRepository extends JpaRepository<ExChangeRate, Long>, QuerydslPredicateExecutor<ExChangeRate> {
    ExChangeRate findByCode(String code);

}



