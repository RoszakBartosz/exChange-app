package com.example.exchange_app.controller;

import com.example.exchange_app.model.ExChangeRateRequest;
import com.example.exchange_app.model.dto.ResponseRatesDTO;
import com.example.exchange_app.model.history.ExChangeHistoryLog;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/ex")
@Tag(name = "wymiana", description = "exchanging the currency")
public class ExChangingController {

    private final ExChangeService service;

    @Operation(summary = "podaj walutę, liczbę i walutę docelową", description = "zwraca ResponseDTO z wartościami")
    @PostMapping("/exchange")
    public ResponseEntity<ResponseDTOExChangeRate> exChange(@Valid @RequestBody RequestExChangeDTO requestDTO) {
        ResponseDTOExChangeRate responseDTOExChangeRate = service.exChange(requestDTO);
        return new ResponseEntity<>(responseDTOExChangeRate, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<ResponseRatesDTO>> findAll(ExChangeRateRequest exChangeRateRequest, @PageableDefault Pageable pageable){
        return new ResponseEntity<>(service.findAll(exChangeRateRequest, pageable), HttpStatus.OK);
    }

    @GetMapping("find-by-code")
    public ResponseEntity<ResponseRatesDTO> findByCode(@RequestParam String code){
        return new ResponseEntity<>(service.findByCode(code), HttpStatus.OK);
    }

    @GetMapping("find-all-history")
    public ResponseEntity<Page<ExChangeHistoryLog>> findAllHistory(@PageableDefault Pageable pageable){
        return new ResponseEntity<>(service.findAllHistory(pageable), HttpStatus.OK);
    }

}
