package com.projetM2_foot.controller;


import com.projetM2_foot.dto.request.ScenarioRequestCreate;
import com.projetM2_foot.dto.response.ScenarioResponseAll;
import com.projetM2_foot.dto.response.ScenarioResponse;
import com.projetM2_foot.entity.Scenario;
import com.projetM2_foot.mapper.ScenarioMapper;
import com.projetM2_foot.service.ScenarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scenario")
@Slf4j
@RequiredArgsConstructor
@Tag(
        name = "Scenario",
        description = "Gestion des scénarios"
)
public class ScenarioController {

    public final ScenarioMapper scenarioMapper;
    public final ScenarioService scenarioService;

    @GetMapping("/getScenario")
    @Operation(summary = "Récupérer un scénario", description = "Récupère les détails d'un scénario.")
    public String getScenario() {
        log.info("Endpoint appelé : GET /scenario/getScenario");
        return "Détails du scénario";
    }

    @PostMapping
    public ResponseEntity<ScenarioResponse> createScenario (@RequestBody ScenarioRequestCreate scenarioRequestCreate){
        final Scenario scenario = scenarioService.create(scenarioRequestCreate);
        final ScenarioResponse dto = scenarioMapper.ToDto(scenario);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping(value = "/test")
    public ResponseEntity<ScenarioResponseAll> getAllScenario(){
        final List<Scenario> scenarioList = scenarioService.getAllScenario();
        final ScenarioResponseAll reponse =scenarioMapper.toGetAll(scenarioList);

        return ResponseEntity.ok(reponse);
    }

    @GetMapping
    public ResponseEntity<List<Scenario>> getAllScenario2(){
        return new ResponseEntity<>(scenarioService.getAllScenario(), HttpStatus.OK);
    }

}
