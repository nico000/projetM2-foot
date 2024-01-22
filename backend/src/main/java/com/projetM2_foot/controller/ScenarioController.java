package com.projetM2_foot.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scenario")
@Slf4j
@RequiredArgsConstructor
@Tag(
        name = "Scenario",
        description = "Gestion des scénarios"
)
public class ScenarioController {

    @GetMapping("/getScenario")
    @Operation(summary = "Récupérer un scénario", description = "Récupère les détails d'un scénario.")
    public String getScenario() {
        log.info("Endpoint appelé : GET /scenario/getScenario");
        return "Détails du scénario";
    }
}
