package com.stock.excel.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Data
@NoArgsConstructor
public class YahooApiService {

    private OkHttpClient client;

    private static final String URL = "https://yahoo-finance15.p.rapidapi.com/api/yahoo/qu/quote/";
    private static final String HOST = "yahoo-finance15.p.rapidapi.com";
    private static final String API_KEY = System.getenv("YAHOO_API");

    @Autowired
    public YahooApiService(OkHttpClient client) {
        this.client = client;
    }


    public Response fetchIncomeStatementFromApi(String stockName) throws IOException {
        Request request = new Request.Builder()
                .url(URL + stockName + "/income-statement")
                .get()
                .addHeader("X-RapidAPI-Key", API_KEY)
                .addHeader("X-RapidAPI-Host", HOST)
                .build();

        return client.newCall(request).execute();
    }

}
