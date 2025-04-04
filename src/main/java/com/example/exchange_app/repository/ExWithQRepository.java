package com.example.exchange_app.repository;

import com.example.exchange_app.model.ExChangeRate;

import java.util.Optional;
//public class ExWithQRepository {
//    private final JPAQueryFactory queryFactory;
//
//    public ExWithQRepository(JPAQueryFactory queryFactory) {
//        this.queryFactory = queryFactory;
//    }
//
//    public Optional<ExChangeRate> findByCurrency(String currency) {
//        QExchangeRate exchangeRate = QExchangeRate.exchangeRate;
//
//        ExchangeRate result = queryFactory
//                .selectFrom(exchangeRate)
//                .where(exchangeRate.currency.eq(currency))
//                .fetchOne();
//
//        return Optional.ofNullable(result);
//    }
//}
