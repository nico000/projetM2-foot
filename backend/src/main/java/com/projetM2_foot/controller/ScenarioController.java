package com.projetM2_foot.controller;


import com.projetM2_foot.api.request.ScenarioRequestCreate;
import com.projetM2_foot.api.request.ScenarioRequestUpdate;
import com.projetM2_foot.api.response.*;
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

import javax.validation.Valid;
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
            @Valid @RequestBody ScenarioRequestCreate request){

        log.info("Endpoint appelé : POST /scenario/");
        final Scenario scenario = scenarioMapper.toEntity(request);
        final Scenario new_scenario = scenarioService.create(scenario);
        final ScenarioResponse dto = scenarioMapper.toDto(new_scenario);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


    @GetMapping("/all")
    @Operation(
        summary = "Lire les scénarios",
        description = "Récupère les scénarios"
    )
    public ResponseEntity<List<ScenarioResponse>> getScenarioAll (){

        log.info("Endpoint appelé : GET /scenario/");
        final List<Scenario> listScenario = scenarioService.getAllScenario();
        final List<ScenarioResponse> response = scenarioMapper.toGetAll(listScenario);

        for(ScenarioResponse res : response){
            // Nombre joueur
            res.setNb_joueur(scenarioService.CountJoueurByScenario(res.getId()));
            // Nombre deplacement
            res.setNb_deplacement(scenarioService.CountDeplacementByScenario(res.getId()));
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{scenario}")
    @Operation(
            summary = "Supprime un scénario",
            description = "Supprime un scénario de la base de donnée par un id de scénario")
    public ResponseEntity<?> deleteScenario (
            @PathVariable
            Long scenario
    ){

        log.info("Endpoint appelé : DELETE /scenario/" + scenario);
        scenarioService.deleteScenario(scenario);
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


    @GetMapping(params = "nom")
    public ResponseEntity<ScenarioResponse> getScenarioNom (@RequestParam String nom){

        log.info("Endpoint appelé : GET /scenario?nom="+nom);

        final Scenario scenario = scenarioService.getScenarioByName(nom);
        final ScenarioResponse response = scenarioMapper.toDto(scenario);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{idScenario}")
    public ResponseEntity<ScenarioResponse> getScenarioId(@PathVariable Long idScenario){

        log.info("Endpoint appelé : GET /scenario/" + idScenario);

        final Scenario scenario = scenarioService.getScenario(idScenario);
        final ScenarioResponse response = scenarioMapper.toDto(scenario);

        return ResponseEntity.ok(response);

    }


}
