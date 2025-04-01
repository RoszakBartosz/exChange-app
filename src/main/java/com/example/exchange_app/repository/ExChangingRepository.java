package com.example.exchange_app.repository;

import com.example.exchange_app.model.ExChangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExChangingRepository extends JpaRepository<ExChangeRate, Long> {
    ExChangeRate findByCode(String code);
}



