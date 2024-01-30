package com.projetM2_foot.controller;



import com.projetM2_foot.api.request.ScenarioRequestCreate;
import com.projetM2_foot.api.response.ScenarioResponse;
import com.projetM2_foot.entity.Scenario;
import com.projetM2_foot.mapper.ScenarioMapper;
import com.projetM2_foot.service.ScenarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
// lombok.extern.slf4j.Slf4j;
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
    @Operation(
            summary = "Récupérer un scénario [NON FONCTIONNELLE]",
            description = "Récupère les détails d'un scénario.")
    public String getScenario() {
        log.info("Endpoint appelé : GET /scenario/getScenario");
        return "Détails du scénario";
    }

    @PostMapping
    @Operation(
            summary = "Créer un scénario",
            description = "Créer une situation simple à partir des informations donné")
    public ResponseEntity<ScenarioResponse> createScenario (
            @RequestBody ScenarioRequestCreate scenarioRequestCreate){

        final Scenario scenario = scenarioService.create(scenarioRequestCreate);
        final ScenarioResponse dto = scenarioMapper.toDto(scenario);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


    @GetMapping
    @Operation(
            summary = "Lire les scénarios",
            description = "Récupère les scénarios")
    public ResponseEntity<List<ScenarioResponse>> GetScenarioAll (){
        final List<Scenario> listScenario = scenarioService.getAllScenario();
        final List<ScenarioResponse> response = scenarioMapper.toGetAll(listScenario);
        return ResponseEntity.ok(response);
    }

}
