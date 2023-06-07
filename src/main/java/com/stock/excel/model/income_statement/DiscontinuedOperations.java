package com.stock.excel.model.income_statement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscontinuedOperations {
    List<DiscontinuedOperations> discontinuedOperations;
}
