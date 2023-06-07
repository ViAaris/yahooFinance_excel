package com.stock.excel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeStatementDTO {

    private String endDate;
    private String totalRevenue;
    private String costOfRevenue;
    private String grossProfit;
    private String totalOperatingExpenses;
    private String operatingIncome;
    private String totalOtherIncomeExpenseNet;
    private String ebit;
    private String netIncome;
    private String netIncomeFromContinuingOps;
}
