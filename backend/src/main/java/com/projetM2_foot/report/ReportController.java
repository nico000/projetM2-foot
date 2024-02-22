package com.projetM2_foot.report;


import com.projetM2_foot.entity.Scenario;
import com.projetM2_foot.report.mapper.ReportMapper;
import com.projetM2_foot.report.response.ReportScenario;
import com.projetM2_foot.service.ScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/export")
public class ReportController {

    @Autowired
    ReportService userReportService;
    @Autowired
    ScenarioService scenarioService;
    @Autowired
    ReportMapper reportMapper;


    @GetMapping("/all")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        this.userReportService.exportToExcel(response);
    }

    @GetMapping("/scenario")
    public ResponseEntity<List<ReportScenario>> getScenarioReport() {

        List<Scenario> data = scenarioService.getAllScenario();
        List<ReportScenario> sceneList = data.stream().map(reportMapper::toDtoScenario).collect(Collectors.toList());
        return ResponseEntity.ok().body(sceneList);
    }



}