package com.projetM2_foot.report;

import com.projetM2_foot.entity.ResultatExamen;
import com.projetM2_foot.report.mapper.ReportMapper;
import com.projetM2_foot.report.response.ReportResultatExamen;
import com.projetM2_foot.service.ResultatExamenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportService {


    @Autowired
    final ExcelService excelService;

    @Autowired
    final ResultatExamenService resultatExamenService;

    @Autowired
    final ReportMapper reportMapper;



    public void exportToExcelAll(HttpServletResponse response) throws IOException {

        List<ResultatExamen> data = resultatExamenService.getAll();
        List<ReportResultatExamen> dataList = data
                .stream().map(reportMapper::toDtoResultatExamen)
                .collect(Collectors.toList());
        excelService.exportToExcel(response, dataList);

    }

    public void exportToExcelPerson(
            HttpServletResponse response,
            String nom,
            String prenom) throws IOException {

        List<ResultatExamen> data = resultatExamenService.getByNomPrenom(nom,prenom);
        List<ReportResultatExamen> dataList = data
                .stream().map(reportMapper::toDtoResultatExamen)
                .collect(Collectors.toList());
        excelService.exportToExcel(response, dataList);
    }


}