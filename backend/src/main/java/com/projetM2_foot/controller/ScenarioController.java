package com.projetM2_foot.controller;



import com.projetM2_foot.api.request.ScenarioRequestCreate;
import com.projetM2_foot.api.response.ScenarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
// lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scenario")
@Slf4j
@RequiredArgsConstructor
@Tag(
        name = "Scenario",
        description = "Gestion des scénarios"
)
public class ScenarioController {

    //public final ScenarioMapper scenarioMapper;
    //public final ScenarioService scenarioService;

    @GetMapping("/getScenario")
    @Operation(
            summary = "Récupérer un scénario",
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

        //log.info("Creating equipement from {}",scenarioRequestCreate);
      //  final Scenario scenario = scenarioService.create(scenarioRequestCreate);
       // final ScenarioResponse dto = scenarioMapper.ToDto(scenario);

    //    return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        return null;
    }



}
