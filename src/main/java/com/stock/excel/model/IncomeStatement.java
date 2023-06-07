package com.stock.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.stock.excel.model.income_statement.*;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class IncomeStatement {

    private EndDate endDate;
    private TotalRevenue totalRevenue;
    private CostOfRevenue costOfRevenue;
    private GrossProfit grossProfit;
    private TotalOperatingExpenses totalOperatingExpenses;
    private OperatingIncome operatingIncome;
    private TotalOtherIncomeExpenseNet totalOtherIncomeExpenseNet;
    private Ebit ebit;
    private NetIncome netIncome;
    private NetIncomeFromContinuingOps netIncomeFromContinuingOps;
//    private DiscontinuedOperations discontinuedOperations;


}
