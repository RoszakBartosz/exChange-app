package com.example.exchange_app.model;

import com.querydsl.core.annotations.QueryEntities;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "exchangerate")
@AllArgsConstructor
@Builder
public class ExChangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String currency;
    private String code;
    private BigDecimal mid;
}
