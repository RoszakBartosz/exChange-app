package com.example.exchange_app.service;

import com.example.exchange_app.model.CacheConstans;
import com.example.exchange_app.model.ExChangeRateRequest;
import com.example.exchange_app.model.QExChangeRate;
import com.example.exchange_app.model.dto.CalculatorResponseDTO;
import com.example.exchange_app.model.dto.ResponseRatesDTO;
import com.example.exchange_app.model.history.ExChangeHistoryRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.exchange_app.model.ExChangeRate;
import com.example.exchange_app.model.dto.RequestExChangeDTO;
import com.example.exchange_app.model.dto.ResponseDTOExChangeRate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.exchange_app.repository.ExChangingRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExChangeService {
    private final ExChangingRepository repository;
    private final CalculatorService calculatorService;
    private final ExChangingHistoryLogService historyLogService;
    private final AuthenticationService authenticationService;



        @Cacheable(value = CacheConstans.FIND_ALL, key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort + '-' + #exChangeRateRequest.getFromMid + '-' + #exChangeRateRequest.getToMid + '-' + #exChangeRateRequest.getCode")
    public Page<ResponseRatesDTO> findAll(ExChangeRateRequest exChangeRateRequest, Pageable pageable) {
            System.out.println("REQ: code=" + exChangeRateRequest.getCode()
                    + ", fromMid=" + exChangeRateRequest.getFromMid()
                    + ", toMid=" + exChangeRateRequest.getToMid());
            QExChangeRate exchangeRate = QExChangeRate.exChangeRate;

        BooleanExpression predicate = buildPredicate(exChangeRateRequest, exchangeRate);

        Page<ExChangeRate> all = repository.findAll(predicate, pageable);
        List<ResponseRatesDTO> collect = all.stream().map(exChangeRate -> {
            ResponseRatesDTO build = ResponseRatesDTO.builder()
                    .currency(exChangeRateRequest.getCurrency())
                    .mid(exChangeRate.getMid())
                    .code(exChangeRate.getCode())
                    .build();
            return build;
        }).collect(Collectors.toList());
        Page<ResponseRatesDTO> page = new PageImpl<>(collect, pageable, all.getTotalElements());
        return page;
    }

    private BooleanExpression buildPredicate(ExChangeRateRequest exChangeRateRequest, QExChangeRate exChangeRate) {
        BooleanExpression predicate = exChangeRate.isNotNull();

        String currency = exChangeRateRequest.getCode();
        BigDecimal fromMid = exChangeRateRequest.getFromMid();
        BigDecimal toMid = exChangeRateRequest.getToMid();


        if (Objects.nonNull(currency) && !currency.isEmpty()) {
            predicate = predicate.and(exChangeRate.code.equalsIgnoreCase(currency));
        }

        if (Objects.nonNull(toMid)) {
            predicate = predicate.and(exChangeRate.mid.loe(exChangeRateRequest.getToMid()));
        }

        if (Objects.nonNull(fromMid)) {
            predicate = predicate.and(exChangeRate.mid.goe(exChangeRateRequest.getFromMid()));
        }
        return predicate;

    }


    @Cacheable(value = CacheConstans.FIND_BY_CODE, key = "#code")
    public ResponseRatesDTO findByCode(String code){

        ExChangeRate byCode = repository.findByCode(code).orElseThrow(() -> new NullPointerException("Not correct code"));
        ResponseRatesDTO exc = ResponseRatesDTO.builder()
                .currency(byCode.getCurrency())
                .code(byCode.getCode())
                .mid(byCode.getMid())
                .build();
        return exc;
    }
    @Transactional
    @CacheEvict(value = {CacheConstans.FIND_BY_CODE, CacheConstans.FIND_ALL}, allEntries = true)
    public ResponseDTOExChangeRate exChange(RequestExChangeDTO requestExChangeDTO){
        System.out.println("REQUEST: "+requestExChangeDTO.getCodeCurrencyTo());
        CalculatorResponseDTO calculatorResponseDTO = calculatorService.ExChange(requestExChangeDTO.getCodeCurrencyFrom(),
                 requestExChangeDTO.getCodeCurrencyTo(), requestExChangeDTO.getValueFrom());
        BigDecimal value = calculatorResponseDTO.getValue();
        if (value ==null|| value.signum()<=0){
            throw new NullPointerException("amount from calculator is equal 0: ");
        }
        ResponseDTOExChangeRate responseDTO = ResponseDTOExChangeRate.builder()
                .codeFrom(requestExChangeDTO.getCodeCurrencyFrom())
                .amountFrom(requestExChangeDTO.getValueFrom())
                .codeTo(requestExChangeDTO.getCodeCurrencyTo())
                .amountTo(value)
                .mid(calculatorResponseDTO.getMid())
                .build();
        return responseDTO;
    }
}
