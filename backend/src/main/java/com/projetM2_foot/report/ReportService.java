package com.projetM2_foot.report;

import com.projetM2_foot.entity.Scenario;
import com.projetM2_foot.report.mapper.ReportMapper;
import com.projetM2_foot.report.response.ReportScenario;
import com.projetM2_foot.service.ScenarioService;
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
    final ScenarioService scenarioService;

    @Autowired
    final ReportMapper reportMapper;



    public void exportToExcel(HttpServletResponse response) throws IOException {

        // get all scenario
        List<Scenario> data = scenarioService.getAllScenario();

        List<ReportScenario> sceneList = data.stream().map(reportMapper::toDtoScenario).collect(Collectors.toList());

        System.out.println(sceneList);

        excelService.exportToExcel(response, sceneList);

    }


}