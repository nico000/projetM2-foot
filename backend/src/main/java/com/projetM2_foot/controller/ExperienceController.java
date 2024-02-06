package com.projetM2_foot.controller;


import com.projetM2_foot.api.request.deplacement.DeplacementRequestCreate;
import com.projetM2_foot.api.request.experience.ExperienceRequestCreate;
import com.projetM2_foot.api.response.DeplacementResponse;
import com.projetM2_foot.api.response.ExperienceResponse;
import com.projetM2_foot.entity.Deplacement;
import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.mapper.DeplacementMapper;
import com.projetM2_foot.mapper.ExperienceMapper;
import com.projetM2_foot.service.DeplacementService;
import com.projetM2_foot.service.ExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/experience")
@Slf4j
@RequiredArgsConstructor
@Tag(
        name = "Experience",
        description = "Gestion des differente experience crée"
)
public class ExperienceController {

    public final ExperienceMapper experienceMapper;
    public final ExperienceService experienceService;

    @PostMapping
    @Operation(
            summary = "Créer une experience",
            description = "Créer un une experience à partir d'un scénario et y spécifier les paramètre de feedback")
    public ResponseEntity<ExperienceResponse> createExperience (
            @RequestBody ExperienceRequestCreate request){

        log.info("Endpoint appelé : POST /experience/");
        final Experience entity = experienceMapper.toEntity(request);
        final Experience new_entity = experienceService.create(entity);
        final ExperienceResponse dto = experienceMapper.toDto(new_entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{idExperience}")
    @Operation(
            summary = "Récupérer une experience",
            description = "Trouve l'experience lié à l'id donné en paramètre")
    public ResponseEntity<ExperienceResponse> getExperience(
            @PathVariable Long idExperience
    ){

        log.info("Endpoint appelé : GET /experience/" + idExperience);

        final Experience entity = experienceService.getById(idExperience);
        final ExperienceResponse dto = experienceMapper.toDto(entity);

        return ResponseEntity.ok(dto);
    }




}
