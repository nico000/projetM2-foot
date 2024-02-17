package com.projetM2_foot.controller;


import com.projetM2_foot.api.request.ExperienceRequestCreate;
import com.projetM2_foot.api.response.ExperienceResponse;
import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.mapper.ExperienceMapper;
import com.projetM2_foot.service.ExperienceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Experience entity = experienceMapper.toEntity(request);
        entity = experienceService.create(entity);
        ExperienceResponse dto = experienceMapper.toDto(entity);
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
        Experience entity = experienceService.getById(idExperience);
        ExperienceResponse dto = experienceMapper.toDto(entity);

        return ResponseEntity.ok(dto);
    }

}
