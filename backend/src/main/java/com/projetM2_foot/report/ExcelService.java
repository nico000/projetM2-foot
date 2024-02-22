package com.projetM2_foot.report;

import com.projetM2_foot.report.response.ReportEntite;
import com.projetM2_foot.report.response.ReportScenario;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExcelService extends ReportAbstract {



    /**
        Créer une zone
    */
    public void createObjectCell(int row_init , int column_init ,List<Object> dataObject){

        List<String> propertyNames = Arrays
                .stream(getPropertyNameByClass(dataObject.get(0).getClass()))
                .collect(Collectors.toList());


        // font style content
        CellStyle style = getFontContentExcel();

        // starting write on row
        int startRow = row_init;

        // write content
        for (Object inst : dataObject) {
            Row row = sheet.createRow(startRow++);
            int columnCount = column_init;

            for (String name_colon : propertyNames){

                try{
                    Object prop = getPropertyValue(inst, name_colon);

                    if(!String.valueOf(prop).isEmpty() && prop != null ){
                        if(!(prop.getClass() == ArrayList.class)) {
                            createCell(row_init, columnCount++, prop, style);
                        }
                        else {
                            columnCount++;
                        }
                    }
                    else{
                        createCell(row_init, columnCount++, "NA" , style);
                    }


                }
                catch (IllegalAccessException e) {
                    createCell(row_init, columnCount++, "NA" , style);
                }
            }
        }
    }

    public void writeTableHeaderExcel(String titleName, String[] headers , int rowInit , int columnInit) {


        CellStyle style = headerStyle;
        XSSFFont font = headerFont;

        // title
        createCell(rowInit, columnInit, titleName, style);
        sheet.addMergedRegion(new CellRangeAddress(rowInit, rowInit, columnInit, columnInit + headers.length - 1));
        font.setFontHeightInPoints((short) 10);

        // header
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        for (int i = 0; i < headers.length; i++) {
            createCell(rowInit+1, i+columnInit, headers[i], style);
        }
    }



    public void exportToExcel(HttpServletResponse response, Object data) throws IOException {

        String filename = "BetaFoot";
        String sheetName = "Feuille global";

        newReportExcel();

        // response  writer to excel
        response = initResponseForExportExcel(response, filename);
        ServletOutputStream outputStream = response.getOutputStream();

        createSheet(sheetName);



        // write sheet, title & header
        //String[] headers = new String[]{"No", "username", "Password", "Roles", "Permission", "Active", "Bloked", "Created By", "Created Date", "Update By", "Update Date"};

        String[] headerScenario = getPropertyNameByClass(ReportScenario.class);
        String[] headerEntite = getPropertyNameByClass(ReportEntite.class);

        int row = 0;
        int column = 0;


        writeTableHeaderExcel("Scenario", headerScenario ,row , column);
        column = column + headerScenario.length;
        writeTableHeaderExcel("Entite", headerEntite ,row , column);
        column = column + headerScenario.length + 1;


        column = 0;
        row =+ 2;
        createObjectCell(row , column , (List<Object>) data );


        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


    public String[] getPropertyNameByClass(Class<?> object){

        List<String> propertyNames = new ArrayList<>();
        Field[] fields = object.getDeclaredFields();
        for (Field field : fields) {
            propertyNames.add(field.getName());
        }
        return propertyNames.toArray(new String[0]);
    }
}