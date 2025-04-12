package com.example.exchange_app.repository;

import com.example.exchange_app.model.ExChangeRate;
import com.example.exchange_app.service.CalculatorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Objects;
import java.util.Optional;

public interface ExChangingRepository extends JpaRepository<ExChangeRate, Long>, QuerydslPredicateExecutor<ExChangeRate> {
    @Query("SELECT e FROM ExChangeRate e WHERE e.code = :code")
    Optional<ExChangeRate> findByCode(@Param("code")String code);

}



