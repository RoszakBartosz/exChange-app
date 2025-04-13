package com.example.exchange_app.service;

import com.example.exchange_app.model.ExChangeRateRequest;
import com.example.exchange_app.model.QExChangeRate;
import com.example.exchange_app.model.history.ExChangeHistoryRequest;
import com.example.exchange_app.model.history.QExChangeHistoryLog;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import com.example.exchange_app.model.history.ExChangeHistoryLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.exchange_app.repository.ExChangingHistoryLogRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Table(name = "exchangehistorylog")
public class ExChangingHistoryLogService {
    private final ExChangingHistoryLogRepository repository;


    public void saveHistory(ExChangeHistoryLog exChangingHistoryLog) {
        repository.save(exChangingHistoryLog);
    }

    //  todo queryDSL
    public Page<ExChangeHistoryLog> findAllHistory(ExChangeHistoryRequest exChangeHistoryRequest, Pageable pageable) {

        QExChangeHistoryLog exChangeHistoryLog = QExChangeHistoryLog.exChangeHistoryLog;

        BooleanExpression predicate = buildPredicate(exChangeHistoryRequest, exChangeHistoryLog);
        Page<ExChangeHistoryLog> all = repository.findAll(predicate, pageable);
        return all;
    }

    public void saveReport(ExChangeHistoryRequest exChangeHistoryRequest) {
        // Pobieranie wszystkich historii
        Page<ExChangeHistoryLog> allHistory = findAllHistory(exChangeHistoryRequest, null);

        // Przygotowanie listy, która będzie przechowywać dane do raportu
        List<String[]> reportData = new ArrayList<>();

        // Dodaj nagłówki do raportu
        reportData.add(new String[]{"fromAmount", "toAmount", ""}); // Zmień nagłówki na odpowiednie
//        private long id;
//        private BigDecimal fromAmountOperation;
//        private BigDecimal ToAmountOperation;
//        private BigDecimal midInTheseTimeFrom;
//        private BigDecimal midInTheseTimeTo;
//        private String chosenCurrencyFrom;
//        private String chosenCurrencyTo;
//        private LocalDateTime dateTimeFromOperation;
        // Iteracja po historii i dodawanie danych do listy
        for (ExChangeHistoryLog e : allHistory) {
            // Tworzymy nowy wiersz dla każdego elementu historii
            String[] row = new String[3]; // Zmienna w zależności od liczby kolumn w raporcie
            row[0] = e.getField1();  // Zastąp metodami dostępu do danych z obiektu ExChangeHistoryLog
            row[1] = e.getField2();  // Zastąp metodami dostępu do danych z obiektu ExChangeHistoryLog
            row[2] = e.getField3();  // Zastąp metodami dostępu do danych z obiektu ExChangeHistoryLog
            reportData.add(row);
        }

        // Generowanie raportu do pliku CSV
        ReportGeneratorService.generateCsvReport(reportData, "report.csv");
    }
    private BooleanExpression buildPredicate(ExChangeHistoryRequest request, QExChangeHistoryLog exChangeHistoryLog) {
        BooleanExpression predicate = exChangeHistoryLog.isNotNull();

        long id = request.getId();

        if (Objects.nonNull(id) && id > 0) {
            predicate = predicate.and(exChangeHistoryLog.dateTimeFromOperation.after(request.getFromDateTimeFromOperation()));
        }

        if (Objects.nonNull(id) && id > 0) {
            predicate = predicate.and(exChangeHistoryLog.dateTimeFromOperation.before(request.getToDateTimeFromOperation()));
        }

        if (Objects.nonNull(id) && id > 0) {
            predicate = predicate.and(exChangeHistoryLog.chosenCurrencyTo.eq(request.getChosenCurrencyTo()));
        }

        if (Objects.nonNull(id) && id > 0) {
            predicate = predicate.and(exChangeHistoryLog.chosenCurrencyFrom.eq(request.getChosenCurrencyFrom()));
        }

        if (Objects.nonNull(id) && id > 0) {
            predicate = predicate.and(exChangeHistoryLog.midInTheseTimeTo.loe(request.getMidInTheseTimeToTo()));
        }

        if (Objects.nonNull(id) && id > 0) {
            predicate = predicate.and(exChangeHistoryLog.midInTheseTimeTo.goe(request.getMidInTheseTimeToFrom()));
        }

        if (Objects.nonNull(id) && id > 0) {
            predicate = predicate.and(exChangeHistoryLog.midInTheseTimeFrom.loe(request.getMidInTheseTimeFromTo()));
        }

        if (Objects.nonNull(id) && id > 0) {
            predicate = predicate.and(exChangeHistoryLog.midInTheseTimeFrom.goe(request.getMidInTheseTimeFromFrom()));
        }
        return predicate;

    }
}





