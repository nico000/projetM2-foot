package com.projetM2_foot.report;

import com.projetM2_foot.report.response.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFColor;
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
public class ExcelService extends ExcelAbstract {



    /**
        Créer une zone
    */
    public void createObjectCell(List<Object> dataObject , int row_init , int column_init ){

        // starting write on row
        int startRow = row_init;

        // write content
        for (Object inst : dataObject) {

            insertValueObject(inst , startRow , column_init);
            startRow++;

        }
    }

    public void insertValueObject(Object inst ,int row_init , int column_init){

        List<String> propertyNames = Arrays
                .stream(getPropertyNameByClass(inst.getClass()))
                .collect(Collectors.toList());

        // font style content
        CellStyle style = getFontContentExcel();

        int columnCount = column_init;

        for (String name_colon : propertyNames){

            try{
                Object prop = getPropertyValue(inst, name_colon);

                if(!String.valueOf(prop).isEmpty() && prop != null ){

                    if(!(prop.getClass() == ArrayList.class) && !(prop.getClass() == ReportEntite.class)) {
                        createCell(row_init, columnCount++, prop, style);
                    }
                    else {
                        if(prop.getClass() == ArrayList.class){
                            // suite
                            columnCount++;
                            row_init++;
                            createObjectCell((List<Object>) prop, row_init , column_init);
                            columnCount = prop.getClass().getDeclaredFields().length;
                        }
                        else {
                            columnCount++;
                            row_init++;
                            insertValueObject(prop , row_init , columnCount);
                        }



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

    public void writeTableHeaderExcel(String titleName, String[] headers , int rowInit , int columnInit) {


        CellStyle style = headerStyle;
        XSSFFont font = headerFont;

        if(titleName != null) {
            // title
            createCell(rowInit, columnInit, titleName, style);
            sheet.addMergedRegion(new CellRangeAddress(rowInit++, rowInit, columnInit, columnInit + headers.length - 1));
        }

        font.setFontHeightInPoints((short) 10);
        // header
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        for (int i = 0; i < headers.length; i++) {
            createCell(rowInit, i+columnInit, headers[i], style);
        }
    }



    public void exportToExcel(HttpServletResponse response, Object data) throws IOException {

        String filename = "BetaFoot";
        newReportExcel();

        // response  writer to excel
        response = initResponseForExportExcel(response, filename);
        ServletOutputStream outputStream = response.getOutputStream();

        // Infos personnelle
        createSheet("Info personelle");
        String[] headerPerson = getPropertyNameByClass(ReportPerson.class);
        writeTableHeaderExcel(null, headerPerson ,0, 0);

        List<ReportResultatExamen> dataList = (List<ReportResultatExamen>) data;
        insertValueObject(dataList.get(0).getPersonalInformation() , 1 , 0);

        for(int a = 0 ; a < 20 ; a++) {
            sheet.autoSizeColumn(a);
        }

        createSynthetique(data , "Données synthétique");
        // Détail
        createBrut(data , "Données brut");


/*



        // write sheet, title & header
        //String[] headers = new String[]{"No", "username", "Password", "Roles", "Permission", "Active", "Bloked", "Created By", "Created Date", "Update By", "Update Date"};
        String[] headerScenario = getPropertyNameByClass(ReportScenario.class);

        writeTableHeaderExcel("Scenario", headerScenario ,row , column);
        column = column + headerScenario.length;


        column = 0;
        row =+ 2;
        createObjectCell((List<Object>) data , row , column  );
*/

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void createSynthetique(Object dataBrut , String sheetName){

        int row_init = 0;
        int column_init = 0;
        int row = row_init;
        int column = column_init;
        createSheet(sheetName);

        CellStyle style = getFontContentExcel();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillPattern(FillPatternType.BIG_SPOTS);
        style.setFillBackgroundColor(IndexedColors.WHITE.index);

        CellStyle styleError = getFontContentExcel();
        styleError.setFillForegroundColor(IndexedColors.RED1.getIndex());



        CellStyle styleErrorDep = getFontContentExcel();
        styleErrorDep.setFillForegroundColor(new XSSFColor(new java.awt.Color(147,2,2),workbook.getStylesSource().getIndexedColors()));


        CellStyle styleGoodDep = getFontContentExcel();
        styleGoodDep.setFillForegroundColor(new XSSFColor(new java.awt.Color(48, 114, 3),workbook.getStylesSource().getIndexedColors()));


        CellStyle styleGoodTry = workbook.createCellStyle();
        styleGoodTry.cloneStyleFrom(style);
        styleGoodTry.setFillForegroundColor(new XSSFColor(new java.awt.Color(48, 114, 3),workbook.getStylesSource().getIndexedColors()));

        CellStyle styleFailTry = workbook.createCellStyle();
        styleFailTry.cloneStyleFrom(style);
        styleFailTry.setFillForegroundColor(new XSSFColor(new java.awt.Color(236, 22, 54),workbook.getStylesSource().getIndexedColors()));


        CellStyle pourcentStyle = getFontContentExcel();
        pourcentStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));

        //    styleError.setFillForegroundColor(IndexedColors.RED.getIndex());

        row++;

        List<ReportResultatExamen> rexaList = (List<ReportResultatExamen>) dataBrut;

        for( ReportResultatExamen rexa : rexaList ){

            for( ReportResultatExperience rexp : rexa.getResultatExperiences()){

                for(ReportEssai rtry : rexp.getEssais()){

                    column = 0;
                    row++;

                    // SCénario / série
                    createCell(row, column++, rexa.getExamen().getId(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                    createCell(row, column++, rexa.getExamen().getName(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                    createCell(row,column++, rexa.getCreateDate() , rtry.isReussi() ? styleGoodTry : styleFailTry);
                    createCell(row, column++, rexp.getExperience().getScenario().getNom(), rtry.isReussi() ? styleGoodTry : styleFailTry );

                    //Feedback
                    createCell(row, column++, rexa.getExamen().getComplexite(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                    createCell(row, column++, rexa.getExamen().getSequencage(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                    createCell(row, column++, rexp.getExperience().getTypeFeedback(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                    createCell(row, column++, rexa.getExamen().getContenuFeedback(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                    createCell(row, column++, (float) rexp.getExperience().getFreqFeedback() / 100, pourcentStyle);
                    createCell(row, column++, rexp.getExperience().getVisuFeedback(), rtry.isReussi() ? styleGoodTry : styleFailTry );

                    // essai
                    createCell(row, column++, rtry.getNumEssai(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                    createCell(row, column++, rtry.getScore(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                    createCell(row, column++, rtry.getTemps(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                    createCell(row, column++, rtry.isReussi() ? "1" : "0",  rtry.isReussi() ? styleGoodTry : styleFailTry );


                }
                row++;
            }
        }

        String[][] headerList = createHeaderSynthetique(row_init,column_init);
        CellRangeAddress region;
        int col = 0;
        for( int i = 0 ; i < headerList.length-1 ; i++){
            col = col + headerList[i].length;
            region = new CellRangeAddress(0, row, col-1, col-1);

            RegionUtil.setBorderRight(BorderStyle.DOUBLE, region, sheet);
            RegionUtil.setRightBorderColor(IndexedColors.RED.index, region, sheet);


        }

        region = new CellRangeAddress(row_init+1, row_init+1, column_init, column);
        RegionUtil.setBorderBottom(BorderStyle.DASH_DOT, region, sheet);
        RegionUtil.setBorderTop(BorderStyle.DOUBLE , region , sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.index, region, sheet);


        for(int a = 0 ; a < 100 ; a++) {
            sheet.autoSizeColumn(a);
        }
    }


    public String[][] createHeaderSynthetique(int row_init , int column_init){

        CellStyle style = getFontContentExcel();
        style.setAlignment(HorizontalAlignment.CENTER);

        XSSFFont font = workbook.createFont();
        font.setFontHeight(20);
        style.setFont(font);


        // header
        String[] headersSerie = new String[]{
                "ID", "Nom scénario", "Date de création" , "Nom situation" ,
        };

        String[] headersExperience = new String[]{
                "Complexité" , "Séquençage","Format feed-back" , "Type feed-back" ,"Fréquence feed-back", "Nombre de visualisation",
        };

        String[] headersEssai = new String[]{
                "Essai N°", "Score essai", "Temps essai", "Essai réussi",
        };



        String[][] headersList = new String[][]{
                headersSerie, headersExperience, headersEssai,
        };

        // Créer les têtes des headers
        String[] nameHeader = new String[]{ "Scénario" , "Feedback" , "Essai"};
        String[] headers = concatArrays(headersList);
        int column = column_init;
        for (int i = 0 ; i < headersList.length ; i++){
            column = createHeaderHead(row_init, column , headersList[i], nameHeader[i] , style) + 1;
        }


        int row = row_init + 1;
        for(int col = column_init ; col < headers.length ; col++){
            createCell(row, col , headers[col] , headerStyle);
        }



        return headersList;
    }


    public void createBrut(Object dataBrut , String sheetName){

        int row_init = 0;
        int column_init = 0;
        int row = row_init;
        int column = column_init;
        createSheet(sheetName);

        CellStyle style = getFontContentExcel();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillPattern(FillPatternType.BIG_SPOTS);
        style.setFillBackgroundColor(IndexedColors.WHITE.index);

        CellStyle styleError = getFontContentExcel();
        styleError.setFillForegroundColor(IndexedColors.RED1.getIndex());



        CellStyle styleErrorDep = getFontContentExcel();
        styleErrorDep.setFillForegroundColor(new XSSFColor(new java.awt.Color(147,2,2),workbook.getStylesSource().getIndexedColors()));


        CellStyle styleGoodDep = getFontContentExcel();
        styleGoodDep.setFillForegroundColor(new XSSFColor(new java.awt.Color(48, 114, 3),workbook.getStylesSource().getIndexedColors()));


        CellStyle styleGoodTry = workbook.createCellStyle();
        styleGoodTry.cloneStyleFrom(style);
        styleGoodTry.setFillForegroundColor(new XSSFColor(new java.awt.Color(48, 114, 3),workbook.getStylesSource().getIndexedColors()));

        CellStyle styleFailTry = workbook.createCellStyle();
        styleFailTry.cloneStyleFrom(style);
        styleFailTry.setFillForegroundColor(new XSSFColor(new java.awt.Color(236, 22, 54),workbook.getStylesSource().getIndexedColors()));


        CellStyle pourcentStyle = getFontContentExcel();
        pourcentStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));

    //    styleError.setFillForegroundColor(IndexedColors.RED.getIndex());

        row++;

        List<ReportResultatExamen> rexaList = (List<ReportResultatExamen>) dataBrut;

        for( ReportResultatExamen rexa : rexaList ){

            for( ReportResultatExperience rexp : rexa.getResultatExperiences()){

                for(ReportEssai rtry : rexp.getEssais()){

                    int dep_reussi = 0;
                    int dep_total = 0;

                    for(ReportResultatDeplacement rdep : rtry.getDeplacementsRealiser()){

                        column = 0;
                        row++;
                        dep_total++;
                        if(rdep.getReussi()) dep_reussi++;


                        // SCénario / série
                        createCell(row, column++, rexa.getExamen().getId(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                        createCell(row, column++, rexa.getExamen().getName(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                        createCell(row,column++, rexa.getCreateDate() , rtry.isReussi() ? styleGoodTry : styleFailTry);
                        createCell(row, column++, rexp.getExperience().getScenario().getNom(), rtry.isReussi() ? styleGoodTry : styleFailTry );

                        //Feedback
                        createCell(row, column++, rexa.getExamen().getComplexite(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                        createCell(row, column++, rexa.getExamen().getSequencage(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                        createCell(row, column++, rexp.getExperience().getTypeFeedback(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                        createCell(row, column++, rexa.getExamen().getContenuFeedback(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                        createCell(row, column++, (float) rexp.getExperience().getFreqFeedback() / 100, pourcentStyle);
                        createCell(row, column++, rexp.getExperience().getVisuFeedback(), rtry.isReussi() ? styleGoodTry : styleFailTry );

                        // essai
                        createCell(row, column++, rtry.getNumEssai(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                        createCell(row, column++, rtry.getScore(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                        createCell(row, column++, rtry.getTemps(), rtry.isReussi() ? styleGoodTry : styleFailTry );
                        createCell(row, column++, rtry.isReussi() ? "1" : "0",  rtry.isReussi() ? styleGoodTry : styleFailTry );

                        // Déplacements
                        createCell(row, column++, rdep.getAction() ,  rdep.getReussi() ? styleGoodDep : styleErrorDep);
                        createCell(row, column++, rdep.getEntite().getType() == 0 ? "Balle" : "Joueur",  rdep.getReussi() ? styleGoodDep : styleErrorDep);
                        createCell(row, column++, rdep.getEntite().getNumero() ,  rdep.getReussi() ? styleGoodDep : styleErrorDep);

                        createCell(row, column++ , "[ " + rdep.getScenarioPos().getZoneDepartX() + " , " + rdep.getScenarioPos().getZoneDepartY() + " ]", rdep.getReussi() ? styleGoodDep : styleErrorDep);
                        createCell(row, column++ , "[ " + rdep.getScenarioPos().getZoneArriveeX() + " , " + rdep.getScenarioPos().getZoneArriveeY() + " ]", rdep.getReussi() ? styleGoodDep : styleErrorDep);

                        createCell(row, column++ , "[ " + rdep.getEssaiPos().getZoneDepartX() + " , " + rdep.getEssaiPos().getZoneDepartY() + " ]", rdep.getReussi() ? styleGoodDep : styleErrorDep);
                        createCell(row, column++ , "[ " + rdep.getEssaiPos().getZoneArriveeX() + " , " + rdep.getEssaiPos().getZoneArriveeY() + " ]", rdep.getReussi() ? styleGoodDep : styleErrorDep);
                        /*
                        createCell(row, column++, rdep.getEssaiPos().getZoneDepartX() ,  rdep.getReussi() ? styleGoodDep : styleErrorDep);
                        createCell(row, column++, rdep.getEssaiPos().getZoneDepartY() ,  rdep.getReussi() ? styleGoodDep : styleErrorDep);
                        createCell(row, column++, rdep.getEssaiPos().getZoneArriveeX() ,  rdep.getReussi() ? styleGoodDep : styleErrorDep);
                        createCell(row, column++, rdep.getEssaiPos().getZoneArriveeY() ,  rdep.getReussi() ? styleGoodDep : styleErrorDep);
*/

                        // Calcul pourcentage ascendant
                        createCell(row, column, (float) dep_reussi/dep_total , pourcentStyle);


                    }
                    row++;

                }
            }


        }

        String[][] headerList = createHeaderBrut(row_init,column_init);
        CellRangeAddress region;
        int col = 0;
        for( int i = 0 ; i < headerList.length-1 ; i++){
            col = col + headerList[i].length;
            region = new CellRangeAddress(0, row, col-1, col-1);

            RegionUtil.setBorderRight(BorderStyle.DOUBLE, region, sheet);
            RegionUtil.setRightBorderColor(IndexedColors.RED.index, region, sheet);


        }

        region = new CellRangeAddress(row_init+1, row_init+1, column_init, column);
        RegionUtil.setBorderBottom(BorderStyle.DASH_DOT, region, sheet);
        RegionUtil.setBorderTop(BorderStyle.DOUBLE , region , sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.index, region, sheet);


        for(int a = 0 ; a < 100 ; a++) {
            sheet.autoSizeColumn(a);
        }
    }

    public String[][] createHeaderBrut(int row_init , int column_init){

        CellStyle style = getFontContentExcel();
        style.setAlignment(HorizontalAlignment.CENTER);

        XSSFFont font = workbook.createFont();
        font.setFontHeight(20);
        style.setFont(font);



        // header
        String[] headersSerie = new String[]{
                "ID", "Nom scénario", "Date de création" , "Nom situation" ,
        };

        String[] headersExperience = new String[]{
                "Complexité" , "Séquençage","Format feed-back" , "Type feed-back" ,"Fréquence feed-back", "Nombre de visualisation",
        };

        String[] headersEssai = new String[]{
                "Essai N°", "Score essai", "Temps essai", "Essai réussi",
                };

        String[] headersDeplacement = new String[]{
                "Déplacement N°", "Type de l'entité", "numéro entité", "Déplacement scénario départ", "Déplacement scénario arrivé" , "Déplacement essai départ" , "Déplacement essai arrivé" , "Score ascendant" ,
        };

        String[][] headersList = new String[][]{
                headersSerie, headersExperience, headersEssai, headersDeplacement
        };

        // Créer les têtes des headers
        String[] nameHeader = new String[]{ "Scénario" , "Feedback" , "Essai" , "Déplacement"};
        String[] headers = concatArrays(headersList);
        int column = column_init;
        for (int i = 0 ; i < headersList.length ; i++){
            column = createHeaderHead(row_init, column , headersList[i], nameHeader[i] , style) + 1;
        }


        int row = row_init + 1;
        for(int col = column_init ; col < headers.length ; col++){
            createCell(row, col , headers[col] , headerStyle);
        }



        return headersList;
    }

    public String[] getPropertyNameByClass(Class<?> object){

        List<String> propertyNames = new ArrayList<>();
        Field[] fields = object.getDeclaredFields();
        for (Field field : fields) {
            propertyNames.add(field.getName());
        }
        return propertyNames.toArray(new String[0]);
    }

    // Fonction pour concaténer plusieurs tableaux de type String[]
    public static String[] concatArrays(String[]... arrays) {
        return Arrays.stream(arrays)
                .flatMap(Arrays::stream)
                .toArray(String[]::new);
    }


    // Créer la tête d'un header et fusionne les colonnes
    public int createHeaderHead(int row , int column , String[] header , String nom , CellStyle style){
        createCell(row, column, nom, style);
        sheet.addMergedRegion(new CellRangeAddress(row, row, column, column + (header.length-1) ));
        return column + (header.length-1);
    }
}