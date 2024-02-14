package com.projetM2_foot.controller;


import com.projetM2_foot.api.request.ExamenRequestCreate;
import com.projetM2_foot.api.request.ExperienceRequestCreate;
import com.projetM2_foot.api.request.ResultatExamenRequestCreate;
import com.projetM2_foot.api.response.ExamenResponse;
import com.projetM2_foot.api.response.ResultatExamenResponse;
import com.projetM2_foot.entity.Examen;
import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.entity.ResultatExamen;
import com.projetM2_foot.mapper.ExamenMapper;
import com.projetM2_foot.mapper.ExperienceMapper;
import com.projetM2_foot.mapper.ResultatExamenMapper;
import com.projetM2_foot.repository.ExamenRepository;
import com.projetM2_foot.service.ExamenService;
import com.projetM2_foot.service.ExperienceService;
import com.projetM2_foot.service.ResultatExamenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    @Operation(
            summary = "Créer un résultat d'examen",
            description = "Créer une entité qui rassemble des résultat des examens")
    public ResponseEntity<ResultatExamenResponse> createResultatExamen (
            @RequestBody ResultatExamenRequestCreate request){

        log.info("Endpoint appelé : POST /resultat_examen");

        ResultatExamen entity = resultatExamenMapper.toEntity(request);
        if(entity == null) return ResponseEntity.notFound().build();
        entity = resultatExamenService.create(entity);
        ResultatExamenResponse dto = resultatExamenMapper.toDto(entity);

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
        if(entity == null) return ResponseEntity.notFound().build();
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




}
