package com.projetM2_foot.controller;


import com.projetM2_foot.api.response.ResultatExperienceResponse;
import com.projetM2_foot.entity.ResultatExperience;
import com.projetM2_foot.mapper.ResultatExperienceMapper;
import com.projetM2_foot.service.ResultatExperienceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resultat_experience")
@Slf4j
@RequiredArgsConstructor
@Tag(
        name = "Resultat Experience",
        description = "Gestion des differents résultat des expérience"
)
public class ResultatExperienceController {


    final ResultatExperienceService resultatExperienceService;
    final ResultatExperienceMapper resultatExperienceMapper;

    @GetMapping("/{id}")
    @Operation(
            summary = "Récupérer un résultat d'une experience",
            description = "Trouve le résultat d'experience lié à l'id donné en paramètre")
    public ResponseEntity<ResultatExperienceResponse> getResultatExperienceById(
            @PathVariable Long id
    ){

        log.info("Endpoint appelé : GET /resultat_experience/" + id);

        ResultatExperience entity = resultatExperienceService.getById(id);
        if(entity == null) return ResponseEntity.notFound().build();
        ResultatExperienceResponse dto = resultatExperienceMapper.toDto(entity);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Récupérer tout les resultat d'experience",
            description = "Trouve l'ensemble des resultat experience")
    public ResponseEntity<List<ResultatExperienceResponse>> getResultatExperience(
    ){

        log.info("Endpoint appelé : GET /resultat_experience/all");

        List<ResultatExperience> listEntity = resultatExperienceService.getAll();
        List<ResultatExperienceResponse> dtos = resultatExperienceMapper.toDtoAll(listEntity);
        return ResponseEntity.ok(dtos);
    }

}
