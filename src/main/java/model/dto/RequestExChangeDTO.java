package model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RequestExChangeDTO {
    private String codeCurrencyFrom;
    private String codeCurrencyTo;
    private BigDecimal valueFrom;
}
