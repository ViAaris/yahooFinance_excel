import com.stock.excel.Main;
import com.stock.excel.dto.IncomeStatementDTO;
import com.stock.excel.model.ResponseFromApi;
import com.stock.excel.service.ExcelService;
import com.stock.excel.service.Mapper;
import com.stock.excel.service.UserService;
import com.stock.excel.service.YahooApiService;


import okhttp3.Response;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@SpringBootTest(classes = Main.class)
@RunWith(SpringRunner.class)
class MainTest {

    @Autowired
    YahooApiService yahooApiService;

    @Autowired
    UserService userService;

    @Autowired
    ExcelService excelService;

    @Autowired
    Mapper mapper;

    File fileName = new File("C:\\Users\\Viktoria.DESKTOP-1FHOOG6\\Fund.xlsx");

    @Test
    void getEnvVar() {
        System.out.println(System.getenv("YAHOO_API"));
    }

    @Test
    public void testFetchIncomeStatementFromApi() throws IOException {
        Response response = yahooApiService.fetchIncomeStatementFromApi("AAPL");
        System.out.println(response.body().string());
    }

    @Test
    public void testPath(){
        System.out.println(System.getenv("USERPROFILE"));
    }

    @Test
    public void testMapApiResponseToPojo() throws IOException {
        ResponseFromApi responseFromApi = mapper.mapApiResponseToPojo("AAPL");
        System.out.println(responseFromApi);
    }

    @Test
    public void testMapPojoToDTO() throws IOException {
        List<IncomeStatementDTO> dtos = mapper.mapPojoToDTO("AAPL");
        dtos.forEach(System.out::println);
    }

    @Test
    public void testGetSideHeaders() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {


        FileOutputStream fos = new FileOutputStream(fileName);
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Income statement");
        // заполняем список какими-то данными
        List<IncomeStatementDTO> dataList = this.mapper.mapPojoToDTO("AAPL");

        // счетчик для строк
        int rowNum = 0;


        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        Row dateRow = sheet.createRow(rowNum);

        for (int i = 0; i < dataList.size(); i++) {
            dateRow.createCell(i + 1).setCellValue(dataList.get(i).getEndDate());
        }
        List<String> sideHeaders = excelService.getSideHeaders();

        for (String sideHeader : sideHeaders) {
            int cellNumber = 0;
            Row newRow = sheet.createRow(rowNum = rowNum + 1);
            newRow.createCell(cellNumber).setCellValue(sideHeader);
            for (IncomeStatementDTO dto : dataList) {
                newRow.createCell(++cellNumber).setCellValue(excelService.getCellValue(sideHeader, dto));
            }
        }


        workbook.write(fos);
        fos.flush();
        fos.close();
    }

    @Test
    public void testAskUser() throws IOException {
//        userService.askUser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        while(!line.equals("exit")){
            line = reader.readLine();
        }
    }

}