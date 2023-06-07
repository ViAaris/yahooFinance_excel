package com.stock.excel.model.income_statement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Field {

     Long raw;
     String fmt;
     String longFmt;
}
