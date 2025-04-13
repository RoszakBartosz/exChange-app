package com.example.exchange_app.model;

import lombok.Data;

import java.util.List;
@Data
public class ExChangeRateTable {

    private String table;
    private String no;
    private String effectiveDate;
    private List<ExChangeRate> rates;

}
