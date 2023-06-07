package com.stock.excel.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    private String apiType;
    private String stockName;

    public void askUser() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Enter the code of the stock:");
            stockName = reader.readLine();
            System.out.println("Choose the data, press 1 for income statement, 2 for balance sheet");
            String apiNumber = reader.readLine();
            while (!apiNumber.equals("1") && !apiNumber.equals("2")) {
                System.out.println("Wrong number, try again");
                apiNumber = reader.readLine();
            }
            switch (apiNumber) {
                case "1" -> {
                    apiType = "Income Statement";
                    return;
                }
                case "2" -> {
                    apiType = "Balance Sheet";
                    return;
                }
            }

        }while (!(reader.readLine().equals("exit")));

    }
}
