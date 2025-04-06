package com.example.exchange_app.service;

import com.example.exchange_app.model.ExChangeRateRequest;
import com.example.exchange_app.model.QExChangeRate;
import com.example.exchange_app.model.dto.CalculatorResponseDTO;
import com.example.exchange_app.model.dto.ResponseRatesDTO;
import com.example.exchange_app.model.history.ExChangeHistoryLog;
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
import org.springframework.web.bind.annotation.RequestMapping;

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




    public Page<ResponseRatesDTO> findAll(ExChangeRateRequest exChangeRateRequest, Pageable pageable) {

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
        Page page = new PageImpl(collect);
        return page;
    }

    private BooleanExpression buildPredicate(ExChangeRateRequest exChangeRateRequest, QExChangeRate exChangeRate) {
        BooleanExpression predicate = exChangeRate.isNotNull();

        String currency = exChangeRateRequest.getCode();

        if (Objects.nonNull(currency) && !currency.isEmpty()) {
            predicate = predicate.and(exChangeRate.code.equalsIgnoreCase(currency));
        }

        if (Objects.nonNull(currency) && !currency.isEmpty()) {
            predicate = predicate.and(exChangeRate.mid.loe(exChangeRateRequest.getToMid()));
        }


        if (Objects.nonNull(currency) && !currency.isEmpty()) {
            predicate = predicate.and(exChangeRate.mid.goe(exChangeRateRequest.getFromMid()));
        }
        return predicate;

    }





    //TODO nazwa cache do enum albo CacheConstans
    @Cacheable(value = "FIND_ALL", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<ResponseRatesDTO> findAll(Pageable pageable){
        Page<ExChangeRate> allRates = repository.findAll(pageable);
        Page<ResponseRatesDTO> map = allRates.map(exChangeRate -> {
            ResponseRatesDTO build = ResponseRatesDTO.builder()
                    .currency(exChangeRate.getCurrency())
                    .code(exChangeRate.getCode())
                    .mid(exChangeRate.getMid())
                    .build();
            return build;
        });
        return map;
    }

    @Cacheable(value = "FIND_BY_CODE", key = "#code")
    public ResponseRatesDTO findByCode(String code){
        String upperCase = code.toUpperCase();
        ExChangeRate byCode = repository.findByCode(upperCase);
        ResponseRatesDTO exc = ResponseRatesDTO.builder()
//                .currency(byCode.getCurrency())
                .code(upperCase)
                .mid(byCode.getMid())
                .build();
        return exc;
    }
    @Transactional
    @CacheEvict(value = {"FIND_BY_CODE", "FIND_ALL"}, allEntries = true)
    public ResponseDTOExChangeRate exChange(RequestExChangeDTO requestExChangeDTO){
        CalculatorResponseDTO calculatorResponseDTO = calculatorService.ExChange(requestExChangeDTO.getCodeCurrencyFrom(),
                requestExChangeDTO.getCodeCurrencyTo(), requestExChangeDTO.getValueFrom());
        BigDecimal value = calculatorResponseDTO.getValue();
        if (value ==null|| value.signum()<=0){
            throw new NullPointerException("amount from calculator is equal 0: "+ value.toString());
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
