package com.example.exchange_app.controller;

import com.example.exchange_app.model.ExChangeRateRequest;
import com.example.exchange_app.model.dto.ResponseRatesDTO;
import com.example.exchange_app.model.history.ExChangeHistoryLog;
import com.example.exchange_app.model.history.ExChangeHistoryRequest;
import com.example.exchange_app.service.ExChangingHistoryLogService;
import com.example.exchange_app.service.ReportGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.exchange_app.model.dto.RequestExChangeDTO;
import com.example.exchange_app.model.dto.ResponseDTOExChangeRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.exchange_app.service.ExChangeService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ex")
@Tag(name = "wymiana", description = "exchanging the currency")
public class ExChangingController {

    private final ExChangeService service;
    private final ExChangingHistoryLogService historyLogService;
    private final ReportGeneratorService reportGeneratorService;

    @Operation(summary = "podaj walutę, liczbę i walutę docelową", description = "zwraca ResponseDTO z wartościami")
    @PostMapping("/exchange")
    public ResponseEntity<ResponseDTOExChangeRate> exChange(@Valid @RequestBody RequestExChangeDTO requestDTO) {
        ResponseDTOExChangeRate responseDTOExChangeRate = service.exChange(requestDTO);
        return new ResponseEntity<>(responseDTOExChangeRate, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<ResponseRatesDTO>> findAll(@ModelAttribute ExChangeRateRequest exChangeRateRequest, @PageableDefault Pageable pageable){
        return new ResponseEntity<>(service.findAll(exChangeRateRequest, pageable), HttpStatus.OK);
    }

    @GetMapping("find-by-code")
    public ResponseEntity<ResponseRatesDTO> findByCode(@Valid @RequestParam String code){
        return new ResponseEntity<>(service.findByCode(code), HttpStatus.OK);
    }

    @GetMapping("find-all-history")
    public ResponseEntity<Page<ExChangeHistoryLog>> findAllHistory(ExChangeHistoryRequest exChangeHistoryRequest, @PageableDefault Pageable pageable){
        return new ResponseEntity<>(historyLogService.findAllHistory(exChangeHistoryRequest,pageable), HttpStatus.OK);
    }

    @PostMapping("generate-report-high")
    public ResponseEntity<List<ExChangeHistoryLog>> reportHigh(){
        return new ResponseEntity<>(reportGeneratorService
                .getHighAmountTransactionsFromLastMonth(),HttpStatus.OK);
    }
    @PostMapping("generate-report-currency-from")
    public ResponseEntity<Map<String, List<BigDecimal>>> reportFrom(){
        return new ResponseEntity<>(reportGeneratorService.getMonthFrom(),HttpStatus.OK);
    }
    @PostMapping("generate-report-currency-to")
    public ResponseEntity<Map<String, List<BigDecimal>>> reportTo(){
        return new ResponseEntity<>(reportGeneratorService.getMonthTo(),HttpStatus.OK);
    }
}
