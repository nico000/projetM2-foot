package com.projetM2_foot.report;


import com.projetM2_foot.entity.*;
import com.projetM2_foot.report.mapper.ReportMapper;
import com.projetM2_foot.report.response.*;
import com.projetM2_foot.service.ExamenService;
import com.projetM2_foot.service.ResultatExamenService;
import com.projetM2_foot.service.ScenarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService userReportService;
    @Autowired
    ScenarioService scenarioService;
    @Autowired
    ExamenService examService;
    @Autowired
    ReportMapper reportMapper;

    @Autowired
    ResultatExamenService resExamService;


    // Partie rapport en excel

    @GetMapping("/export/all")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        this.userReportService.exportToExcelAll(response);
    }

    @GetMapping("/export")
    public void exportToExcel(
            HttpServletResponse response,
            @RequestParam String nom,
            @RequestParam String prenom) throws IOException {
        System.out.println("nom:" +nom+"prenom"+prenom);
        this.userReportService.exportToExcelPerson(response,nom,prenom);
    }

    // Partie rapport en json

    @GetMapping("/scenario")
    public ResponseEntity<List<ReportScenario>> getScenarioReport() {

        List<Scenario> data = scenarioService.getAllScenario();
        List<ReportScenario> sceneList = data
                .stream()
                .map(reportMapper::toDtoScenario)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(sceneList);
    }

    @GetMapping("/serie")
    public ResponseEntity<List<ReportExamen>> getSerieReport() {

        List<Examen> data = examService.getAll();
        List<ReportExamen> serieList = data
                .stream()
                .map(reportMapper::toDtoExamen)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(serieList);
    }

    @GetMapping("/resultats")
    public ResponseEntity<List<ReportResultatExamen>> getResultatReport() {

        List<ResultatExamen> data = resExamService.getAll();
        List<ReportResultatExamen> serieList =
                data.stream().map(reportMapper::toDtoResultatExamen)
                        .collect(Collectors.toList());

        return ResponseEntity.ok().body(serieList);
    }


    @GetMapping("/resultat")
    public ResponseEntity<List<ReportResultatExamen>> getResultatNomPrenomReport(
            @RequestParam String nom,
            @RequestParam String prenom
    ) {
        List<ResultatExamen> data = resExamService.getByNomPrenom(nom,prenom);
        List<ReportResultatExamen> serieList =
                data.stream().map(reportMapper::toDtoResultatExamen)
                        .collect(Collectors.toList());
        return ResponseEntity.ok().body(serieList);
    }


}