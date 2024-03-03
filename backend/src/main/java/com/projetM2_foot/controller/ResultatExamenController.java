package com.projetM2_foot.controller;

import com.projetM2_foot.api.request.ResultatExamenRequestCreate;
import com.projetM2_foot.api.response.ResNomPrenomResponse;
import com.projetM2_foot.api.response.ResultatCreationResponse;
import com.projetM2_foot.api.response.ResultatExamenResponse;
import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.entity.ResultatExamen;
import com.projetM2_foot.entity.ResultatExperience;
import com.projetM2_foot.mapper.ResultatExamenMapper;
import com.projetM2_foot.mapper.ResultatExperienceMapper;
import com.projetM2_foot.service.ResultatExamenService;

import com.projetM2_foot.service.ResultatExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resultat_examen")
@Slf4j
@RequiredArgsConstructor
@Tag(
        name = "Resultat Examen",
        description = "Gestion des differents résultat d'examen"
)
public class ResultatExamenController {

    final ResultatExamenMapper resultatExamenMapper;
    final ResultatExamenService resultatExamenService;

    final ResultatExperienceMapper resultatExperienceMapper;
    final ResultatExperienceService resultatExperienceService;

    @PostMapping
    @Operation(
            summary = "Créer un résultat d'examen",
            description = "Créer une entité qui rassemble des résultat des examens et les resulatat d'expérience associé")
    public ResponseEntity<ResultatCreationResponse> createResultatExamen (
            @RequestBody ResultatExamenRequestCreate request){

        log.info("Endpoint appelé : POST /resultat_examen");
        log.info(request.toString());

        // Créer le résultat d'examen
        ResultatExamen resultatExamen = resultatExamenMapper.toEntity(request);
        resultatExamen = resultatExamenService.create(resultatExamen);

        // Créer le résultat experience
        List<Experience> listExp = new ArrayList<>(resultatExamen.getExamen().getExperienceSet());

        List<Long> listrexp = new ArrayList<>();

        for(Experience exp : listExp ){
            ResultatExperience rexp = resultatExperienceMapper.toEntity(resultatExamen.getId() , exp.getId());
            rexp = resultatExperienceService.create(rexp);
            listrexp.add(rexp.getId());
        }

        ResultatCreationResponse dto = resultatExamenMapper.toDtoCreation(resultatExamen.getId() , listrexp);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


    @GetMapping("/{idResultatExamen}")
    @Operation(
            summary = "Récupérer un résultat d'un examen",
            description = "Trouve le résultat d'examen lié à l'id donné en paramètre")
    public ResponseEntity<ResultatExamenResponse> getResultatExamenById(
            @PathVariable Long idResultatExamen
    ){

        log.info("Endpoint appelé : GET /resultat_examen/" + idResultatExamen);

        ResultatExamen entity = resultatExamenService.getById(idResultatExamen);
        ResultatExamenResponse dto = resultatExamenMapper.toDto(entity);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Récupérer tout les resultat d'examen",
            description = "Trouve l'ensemble des resultat examens")
    public ResponseEntity<List<ResultatExamenResponse>> getResultatExamen(
    ){

        log.info("Endpoint appelé : GET /resultat_examen/all");

        List<ResultatExamen> listEntity = resultatExamenService.getAll();
        List<ResultatExamenResponse> dtos = resultatExamenMapper.toDtoAll(listEntity);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/getIdentite")
    @Operation(
            summary = "Récupérer les nom prénoms",
            description = "Trouve l'ensemble des resultat examens")
    public ResponseEntity<List<ResNomPrenomResponse>> getIdentite(
    ){

        //log.info("Endpoint appelé : GET /resultat_examen/all");

        List<ResultatExamen> listEntity = resultatExamenService.getAll();
        List<ResNomPrenomResponse> dtos = listEntity
                .stream()
                .map(resultatExamenMapper::toDtoGetIdentite)
                .collect(Collectors.toList());

        dtos = dtos.stream().distinct().collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

}
