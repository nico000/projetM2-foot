package com.projetM2_foot.controller;


import com.projetM2_foot.api.request.EntiteRequestCreate;
import com.projetM2_foot.api.request.EntiteRequestUpdate;
import com.projetM2_foot.api.response.EntiteResponse;
import com.projetM2_foot.entity.Entite;
import com.projetM2_foot.mapper.EntiteMapper;
import com.projetM2_foot.service.EntiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entite")
@Slf4j
@RequiredArgsConstructor
@Tag(
        name = "Entité",
        description = "Gestion des entités"
)
public class EntiteController {

    public final EntiteMapper entiteMapper;
    public final EntiteService entiteService;


    @PostMapping
    @Operation(
            summary = "Créer une entité",
            description = "Créer dans la table entité une entité (joueur / ballon / ..) relié à un scénario"
    )
    public ResponseEntity<EntiteResponse> createEntite (
            @RequestBody EntiteRequestCreate request){

        log.info("Endpoint appelé : POST /entite/");

        Entite entite = entiteMapper.toEntity(request);
        entite =  entiteService.create(entite);
        EntiteResponse dto = entiteMapper.toDto(entite);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{scenarioId}")
    @Operation(
            summary = "Récupère plusieurs entités",
            description = "Récupère les entités lié à un scénario")
    public ResponseEntity<List<EntiteResponse>> getEntiteByScenario (
            @Parameter(description = "Id du scénario", example = "12")
            @PathVariable
            Long scenarioId){

        log.info("Endpoint appelé : GET /entite/" + scenarioId);

        List<Entite> listEntite =  entiteService.getByScenario(scenarioId);
        List<EntiteResponse> dtos = entiteMapper.toDtoAll(listEntite);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @DeleteMapping("/{entiteId}")
    @Operation(
            summary = "Supprime une entité",
            description = "Supprime de la base de donnée un entité en fonction de son id")
    public ResponseEntity<?> deleteEntiteById (
            @Parameter(description = "Id de l'entité", example = "12")
            @PathVariable
            Long entiteId){

        log.info("Endpoint appelé : DELETE /entite/" + entiteId);

        entiteService.deleteById(entiteId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/last/{scenarioId}")
    @Operation(
            summary = "Supprime l'entité la plus récente",
            description = "Supprime de la base de donnée une entité en fonction du scénario et de la date la plus récente")
    public ResponseEntity<?> deleteLastEntiteByScenario (
            @Parameter(description = "Id de l'entité", example = "12")
            @PathVariable
            Long scenarioId){

        log.info("Endpoint appelé : DELETE /entite/last/" + scenarioId);

        entiteService.deleteLastEntite(scenarioId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping
    @Operation(
            summary = "Modifie une entité",
            description = "Modifie une entité existante de la base de donnée en une nouvelle entité en fonction du body")
    public ResponseEntity<?> deleteEntiteById (
            @RequestBody EntiteRequestUpdate request){

        log.info("Endpoint appelé : UPDATE /entite/");

        Entite entity = entiteMapper.toEntity(request);
        entity = entiteService.updateEntite(entity);
        EntiteResponse dto = entiteMapper.toDto(entity);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

}
