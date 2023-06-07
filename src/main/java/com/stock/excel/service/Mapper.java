package com.stock.excel.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.excel.dto.IncomeStatementDTO;
import com.stock.excel.model.IncomeStatement;
import com.stock.excel.model.ResponseFromApi;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.Response;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Component
@NoArgsConstructor
@Data
public class Mapper {

    private YahooApiService yahooApiService;
    private ModelMapper modelMapper;

    @Autowired
    public Mapper(YahooApiService yahooApiService, ModelMapper modelMapper) {
        this.yahooApiService = yahooApiService;
        this.modelMapper = modelMapper;
    }


    public ResponseFromApi mapApiResponseToPojo(String stockName) throws IOException {
        Response response = yahooApiService.fetchIncomeStatementFromApi(stockName);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ResponseFromApi resp = objectMapper.readValue(response.body().string(), ResponseFromApi.class);
        return resp;
    }

    public List<IncomeStatementDTO> mapPojoToDTO(String stockName) throws IOException {

        List<IncomeStatementDTO> dtos = new ArrayList<>();

        TypeMap<IncomeStatement, IncomeStatementDTO> propertyMapper = this.modelMapper
                .createTypeMap(IncomeStatement.class, IncomeStatementDTO.class);

        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getCostOfRevenue().getLongFmt(), IncomeStatementDTO::setCostOfRevenue));
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getEbit().getLongFmt(), IncomeStatementDTO::setEbit));
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getEndDate().getFmt(), IncomeStatementDTO::setEndDate));
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getGrossProfit().getLongFmt(), IncomeStatementDTO::setGrossProfit));
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getNetIncome().getLongFmt(), IncomeStatementDTO::setNetIncome));
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getNetIncomeFromContinuingOps().getLongFmt(), IncomeStatementDTO::setNetIncomeFromContinuingOps));
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getOperatingIncome().getLongFmt(), IncomeStatementDTO::setOperatingIncome));
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getTotalOperatingExpenses().getLongFmt(), IncomeStatementDTO::setTotalOperatingExpenses));
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getTotalOtherIncomeExpenseNet().getLongFmt(), IncomeStatementDTO::setTotalOtherIncomeExpenseNet));
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getTotalRevenue().getLongFmt(), IncomeStatementDTO::setTotalRevenue));

        for (IncomeStatement ist : mapApiResponseToPojo(stockName).getIncomeStatementHistory().getIncomeStatementHistory()) {
            IncomeStatementDTO dto = modelMapper.map(ist, IncomeStatementDTO.class);
            dtos.add(dto);
        }

        return dtos;
    }

}
