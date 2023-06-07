package com.stock.excel;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

import com.stock.excel.service.ExcelService;
import com.stock.excel.service.Mapper;
import com.stock.excel.service.UserService;
import okhttp3.OkHttpClient;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    @Bean
    public OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }




    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        UserService userService = applicationContext.getBean(UserService.class);
        ExcelService excelService = applicationContext.getBean(ExcelService.class);
        userService.askUser();
        excelService.printExcel(userService.getApiType(), userService.getStockName());
    }


}
