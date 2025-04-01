package com.example.exchange_app.service;

import com.example.exchange_app.model.dto.CalculatorResponseDTO;
import com.example.exchange_app.model.dto.ResponseRatesDTO;
import com.example.exchange_app.model.history.ExChangeHistoryLog;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.exchange_app.model.ExChangeRate;
import com.example.exchange_app.model.dto.RequestExChangeDTO;
import com.example.exchange_app.model.dto.ResponseDTOExChangeRate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.exchange_app.repository.ExChangingRepository;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class ExChangeService {
    private final ExChangingRepository repository;
    private final CalculatorService calculatorService;
    private final ExChangingHistoryLogService historyLogService;

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

    public Page<ExChangeHistoryLog> findAllHistory(Pageable pageable){
        return historyLogService.findAll(pageable);
    }

}
