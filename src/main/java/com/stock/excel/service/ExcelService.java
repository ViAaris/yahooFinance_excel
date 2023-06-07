package com.stock.excel.service;

import com.stock.excel.dto.IncomeStatementDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    private final Mapper mapper;

    @Autowired
    public ExcelService(Mapper mapper) {
        this.mapper = mapper;
    }

    public String generatePath(String apiType, String stockName){
        return System.getenv("USERPROFILE") + File.separator + apiType + " " + stockName + ".xlsx";
    }

    public List<String> getSideHeaders(){
        Field[] fields = IncomeStatementDTO.class.getDeclaredFields();
        List<String> sideHeaders = new ArrayList<>();
        for(Field header : fields){
            sideHeaders.add(header.getName());
        }
        sideHeaders.remove("endDate");
        return sideHeaders;
    }

    public String getCellValue(String sideHeader, IncomeStatementDTO dto) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Method method = dto.getClass().getMethod("get" + StringUtils.capitalize(sideHeader));
        return (String) method.invoke(dto);
    }

    public XSSFWorkbook printSideHeadersAndData(String stockName) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {


        List<String> sideHeaders = getSideHeaders();
        List<IncomeStatementDTO> dataList = this.mapper.mapPojoToDTO(stockName);
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Income Statement" + " " + stockName);

        int rowNum = 0;

        Row dateRow = sheet.createRow(rowNum);

        for (int i = 0; i < dataList.size(); i++) {
            dateRow.createCell(i + 1).setCellValue(dataList.get(i).getEndDate());
        }
        for (String sideHeader : sideHeaders) {
            int cellNumber = 0;
            Row newRow = sheet.createRow(rowNum = rowNum + 1);
            newRow.createCell(cellNumber).setCellValue(sideHeader);
            for (IncomeStatementDTO dto : dataList) {
                newRow.createCell(++cellNumber).setCellValue(getCellValue(sideHeader, dto));
            }
        }
        return workbook;
    }

    public void printExcel(String apiType, String stockName) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        try (FileOutputStream fos = new FileOutputStream(generatePath(apiType, stockName))){
            try{
                printSideHeadersAndData(stockName).write(fos);
                System.out.println("the Excel file was generated");
            }catch (Exception e){
                System.out.println("Something went wrong - the spreadsheet was not generated");
            }

        };

    }


}
