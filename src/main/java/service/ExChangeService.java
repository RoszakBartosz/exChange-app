package service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import model.ExChangeRate;
import model.dto.RequestExChangeDTO;
import model.dto.ResponseDTOExChangeRate;
import model.history.ExChangeHistoryLog;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.ExChangingRepository;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class ExChangeService {
    private final ExChangingRepository repository;
    private final CalculatorService calculatorService;
    private final ExChangingHistoryLogService historyLogService;

    @Cacheable(value = "FIND_ALL", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<ResponseDTOExChangeRate> findAll(Pageable pageable){
        Page<ExChangeRate> allRates = repository.findAll(pageable);
        Page<ResponseDTOExChangeRate> map = allRates.map(exChangeRate -> {
            ResponseDTOExChangeRate build = ResponseDTOExChangeRate.builder()
                    .codeFrom(exChangeRate.getCode())
                    .mid(exChangeRate.getMid())
                    .build();
            return build;
        });
        return map;
    }

    @Cacheable(value = "FIND_BY_CODE", key = "#code")
    public ResponseDTOExChangeRate findByCode(String code){
        ExChangeRate byCode = repository.findByCode(code);
        ResponseDTOExChangeRate exc = ResponseDTOExChangeRate.builder()
                .codeFrom(code)
                .mid(byCode.getMid())
                .build();
        return exc;
    }
    @Transactional
    @CacheEvict(value = {"FIND_BY_CODE", "FIND_ALL"}, allEntries = true)
    public ResponseDTOExChangeRate exChange(RequestExChangeDTO requestExChangeDTO){
        BigDecimal amount = calculatorService.ExChange(requestExChangeDTO.getCodeCurrencyFrom(),
                requestExChangeDTO.getCodeCurrencyTo(), requestExChangeDTO.getValueFrom());
        if (amount==null||amount.signum()>=0){
            throw new NullPointerException("amount from calculator is equal 0: "+amount.toString());
        }
        ResponseDTOExChangeRate responseDTO = ResponseDTOExChangeRate.builder()

                .codeFrom(requestExChangeDTO.getCodeCurrencyFrom())
                .amountFrom(requestExChangeDTO.getValueFrom())
                .codeTo(requestExChangeDTO.getCodeCurrencyTo())
                .amountTo(amount)
                .build();
        return responseDTO;
    }

}
