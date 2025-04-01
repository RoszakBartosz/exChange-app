package service;

import lombok.RequiredArgsConstructor;
import model.ExChangeRate;
import model.history.ExChangeHistoryLog;
import org.springframework.stereotype.Service;
import repository.ExChangingRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CalculatorService {
    private final ExChangingRepository repository;
    private final ExChangingHistoryLogService historyLogService;

    public BigDecimal ExChange(String codeCurrencyFrom, String codeCurrencyTo, BigDecimal valueFrom){
        BigDecimal midTo = repository.findByCode(codeCurrencyTo).getMid();

        if (!codeCurrencyFrom.equals("PLN")) {
            ExChangeRate currencyFrom = repository.findByCode(codeCurrencyFrom);
            BigDecimal midFrom = currencyFrom.getMid();
            BigDecimal valueFromInPLN = valueFrom.multiply(midFrom);
            BigDecimal value = valueFromInPLN.multiply(midTo);


            ExChangeHistoryLog exChangeHistoryLog = ExChangeHistoryLog.builder()
                    .chosenCurrencyFrom(codeCurrencyFrom)
                    .fromAmountOperation(valueFrom)
                    .chosenCurrencyTo(codeCurrencyTo)
                    .ToAmountOperation(value)
                    .midInTheseTimeFrom(midFrom)
                    .midInTheseTimeTo(midTo)
                    .dateTimeFromOperation(LocalDateTime.now())
                    .build();

            historyLogService.saveHistory(exChangeHistoryLog);

            return value;
        } else {
            BigDecimal value = valueFrom;
            BigDecimal resultFromDivide = midTo.multiply(value);
            ExChangeHistoryLog exChangeHistoryLog = ExChangeHistoryLog.builder()
                    .chosenCurrencyFrom(codeCurrencyFrom)
                    .fromAmountOperation(valueFrom)
                    .chosenCurrencyTo(codeCurrencyTo)
                    .ToAmountOperation(value)
                    .midInTheseTimeFrom(BigDecimal.valueOf(1.00))
                    .midInTheseTimeTo(midTo)
                    .dateTimeFromOperation(LocalDateTime.now())
                    .build();

            historyLogService.saveHistory(exChangeHistoryLog);

            return  resultFromDivide;
        }


    }

}
