package com.projetM2_foot.controller;



import com.projetM2_foot.api.request.ScenarioRequestCreate;
import com.projetM2_foot.api.response.*;
import com.projetM2_foot.api.request.*;
import com.projetM2_foot.entity.Scenario;
import com.projetM2_foot.mapper.ScenarioMapper;
import com.projetM2_foot.service.ScenarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
// lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
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


    @PostMapping
    @Operation(
            summary = "Créer un scénario",
            description = "Créer une situation simple à partir des informations donné")
    public ResponseEntity<ScenarioResponse> createScenario (
            @RequestBody ScenarioRequestCreate request){

        log.info("Endpoint appelé : POST /scenario/");
        final Scenario scenario = scenarioMapper.toEntity(request);
        final Scenario new_scenario = scenarioService.create(scenario);
        final ScenarioResponse dto = scenarioMapper.toDto(new_scenario);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


    @GetMapping
    @Operation(
            summary = "Lire les scénarios",
            description = "Récupère les scénarios")
    public ResponseEntity<List<ScenarioResponse>> getScenarioAll (){

        log.info("Endpoint appelé : GET /scenario/");
        final List<Scenario> listScenario = scenarioService.getAllScenario();
        final List<ScenarioResponse> response = scenarioMapper.toGetAll(listScenario);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{scenarioId}")
    @Operation(
            summary = "Supprime un scénario",
            description = "Supprime un scénario de la base de donnée par un id de scénario")
    public ResponseEntity<?> deleteScenario (
            @PathVariable
            Long scenarioId
    ){

        log.info("Endpoint appelé : DELETE /scenario/" + scenarioId);
        scenarioService.deleteScenario(scenarioId);
        return ResponseEntity.ok().build();
    }


    @PutMapping
    @Operation(
            summary = "Modifie un scénario",
            description = "Modifie les informations de la base de donnée par un id de scénario")
    public ResponseEntity<ScenarioResponse> updateScenario (
            @RequestBody ScenarioRequestUpdate request
    ){

        log.info("Endpoint appelé : UPDATE /scenario/");

        Scenario scenario = scenarioMapper.toEntity(request);
        scenario  = scenarioService.updateScenario(scenario);
        ScenarioResponse dto = scenarioMapper.toDto(scenario);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/nom")
    public ResponseEntity<ScenarioResponse> getScenarioNom (String name){

        final Scenario scenario = scenarioService.getScenarioNom(name);
        final ScenarioResponse response = scenarioMapper.toDto(scenario);

        return ResponseEntity.ok(response);
    }


}
