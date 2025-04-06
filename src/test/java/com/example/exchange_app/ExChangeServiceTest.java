package com.example.exchange_app;

import com.example.exchange_app.model.ExChangeRate;
import com.example.exchange_app.repository.ExChangingRepository;
import com.example.exchange_app.service.CalculatorService;
import com.example.exchange_app.service.ExChangeService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;


@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class ExChangeServiceTest {
    @Mock
    CalculatorService calculatorService;
    @Mock
    ExChangingRepository repository;
    @InjectMocks
    ExChangeService service;

    @Test
    void findByCode(){
        String codeExpected = "EUR";
        ExChangeRate build = ExChangeRate.builder()
                .id(1L)
                .mid(BigDecimal.valueOf(4.44))
                .code("EUR")
                .currency("Euro")
                .build();
        Mockito.when(repository.findByCode(codeExpected)).thenReturn(build);
        service.findByCode(codeExpected)
        Assertions.assertEquals(build,codeExpected);
        }
    }




