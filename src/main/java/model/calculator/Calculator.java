package model.calculator;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Calculator {
    long id;
    BigDecimal amount;
    private String code;
    private double mid;
}
