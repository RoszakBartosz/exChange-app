package controller;

import lombok.RequiredArgsConstructor;
import model.dto.RequestExChangeDTO;
import model.dto.ResponseDTOExChangeRate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.CalculatorService;
import service.ExChangeService;
import service.ImportExChangingRatesService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ex")
public class ExChangingController {
    private final ExChangeService service;
    @PostMapping("/exchange")
    public ResponseEntity<ResponseDTOExChangeRate> exChange(@RequestBody RequestExChangeDTO requestDTO) {
        ResponseDTOExChangeRate responseDTOExChangeRate = service.exChange(requestDTO);
        return new ResponseEntity<>(responseDTOExChangeRate, HttpStatus.OK);
    }

}
