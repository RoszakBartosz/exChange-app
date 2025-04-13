package com.example.exchange_app.other;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//@Component
//public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {
//
//    @Override
//    public BigDecimal convert(String source) {
//        if (source == null || source.isEmpty()) {
//            return null;  // Możesz też rzucić wyjątek, jeśli chcesz
//        }
//        return new BigDecimal(source);
//    }
//}