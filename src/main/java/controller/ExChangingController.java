package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import service.CalculatorService;
import service.ImportExChangingRatesService;

@RequiredArgsConstructor
@RestController
public class ExChangingController {
    private final ImportExChangingRatesService service;
    private final CalculatorService calculator;

}
