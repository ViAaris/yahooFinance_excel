package com.stock.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class IncomeStatementHistory {

    private List<IncomeStatement> incomeStatementHistory;

    public List<IncomeStatement> getIncomeStatementHistory() {
        return incomeStatementHistory;
    }
}
