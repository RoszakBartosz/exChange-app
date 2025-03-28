package model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExChangeRate {
    private String currency;
    private String code;
    private BigDecimal mid;
}
