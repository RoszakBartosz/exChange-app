package com.example.exchange_app;

import com.example.exchange_app.model.ExChangeRate;
import com.example.exchange_app.model.ExChangeRateRequest;
import com.example.exchange_app.model.dto.CalculatorResponseDTO;
import com.example.exchange_app.model.dto.RequestExChangeDTO;
import com.example.exchange_app.model.dto.ResponseRatesDTO;
import com.example.exchange_app.repository.ExChangingRepository;
import com.example.exchange_app.service.CalculatorService;
import com.example.exchange_app.service.ExChangeService;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


@Slf4j
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

            ExChangeRate build1 = ExChangeRate.builder()
                    .mid(BigDecimal.valueOf(4.44))
                    .code("EUR")
                    .currency("Euro")
                    .build();

        ResponseRatesDTO build = ResponseRatesDTO.builder()
                .mid(BigDecimal.valueOf(4.44))
                .code("EUR")
                .currency("Euro")
                .build();

        Mockito.when(repository.findByCode(codeExpected)).thenReturn(Optional.ofNullable(build1));
            ResponseRatesDTO byCode = service.findByCode(codeExpected);
            Assertions.assertEquals(build,byCode);
        }

        @Test
    void exChange(){
        RequestExChangeDTO requestExChangeDTO = RequestExChangeDTO.builder()
                .codeCurrencyFrom("PLN")
                .codeCurrencyTo("USD")
                .valueFrom(BigDecimal.valueOf(50000))
                        .build();

            CalculatorResponseDTO value = CalculatorResponseDTO.builder()
                    .mid(BigDecimal.valueOf(4.24))
                    .value(null)
                            .build();

            Mockito.when(calculatorService.ExChange(any(), any(), any())).thenReturn(value);
            NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
                service.exChange(requestExChangeDTO);
                throw new NullPointerException("amount from calculator is equal 0: ");
            });
        Assertions.assertEquals("amount from calculator is equal 0: ",exception.getMessage());
    }
    @Test
    void shouldReturnFilteredRatesBasedOnRequest() {
        // given
        ExChangeRateRequest request = new ExChangeRateRequest();
        request.setCode("USD");
        request.setFromMid(new BigDecimal("3.5"));
        request.setToMid(new BigDecimal("4.0"));

        Pageable pageable = PageRequest.of(0, 10);

        ExChangeRate mockRate = new ExChangeRate();
        mockRate.setCode("USD");
        mockRate.setMid(new BigDecimal("3.76"));

        List<ExChangeRate> mockList = List.of(mockRate);
        Page<ExChangeRate> mockPage = new PageImpl<>(mockList, pageable, 1);

        Mockito.when(repository.findAll(Mockito.any(BooleanExpression.class), Mockito.eq(pageable)))
                .thenReturn(mockPage);

        // when
        Page<ResponseRatesDTO> result = service.findAll(request, pageable);

        // then
        Assertions.assertEquals(1, result.getTotalElements());
        ResponseRatesDTO dto = result.getContent().get(0);
        Assertions.assertEquals("USD", dto.getCode());
        Assertions.assertEquals(new BigDecimal("3.76"), dto.getMid());
    }
    }




