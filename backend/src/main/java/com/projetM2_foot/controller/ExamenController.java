package com.projetM2_foot.controller;


import com.projetM2_foot.api.request.ExamenRequestCreate;
import com.projetM2_foot.api.request.ExperienceRequestCreate;
import com.projetM2_foot.api.response.ExamenResponse;
import com.projetM2_foot.entity.Examen;
import com.projetM2_foot.entity.Experience;
import com.projetM2_foot.mapper.ExamenMapper;
import com.projetM2_foot.mapper.ExperienceMapper;
import com.projetM2_foot.repository.ExamenRepository;
import com.projetM2_foot.service.ExamenService;
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
@RequestMapping("/examen")
@Slf4j
@RequiredArgsConstructor
@Tag(
        name = "Examen",
        description = "Gestion des differents examens"
)
public class ExamenController {

    public final ExamenMapper examenMapper;
    //public final ExamenService examenService;
    public final ExamenRepository examenRepository;
    public final ExperienceMapper experienceMapper;
    public final ExamenService examenService;
    public final ExperienceService experienceService;

    @PostMapping
    @Operation(
            summary = "Créer un examen",
            description = "Créer un examen")
    public ResponseEntity<ExamenResponse> createExamen (
            @RequestBody ExamenRequestCreate request){

        log.info("Endpoint appelé : POST /examen");
        final Examen entity = examenMapper.toEntity(request);

       // log.info("Examen : "+ entity.getExperienceSet().stream().map(experienceMapper::toDto).collect(Collectors.toList()));
        final Examen new_entity =  examenService.create(entity);

        final ExamenResponse dto = examenMapper.toDto(new_entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


    @GetMapping("/{idExamen}")
    @Operation(
            summary = "Récupérer une examen",
            description = "Trouve l'examen lié à l'id donné en paramètre")
    public ResponseEntity<ExamenResponse> getExamenById(
            @PathVariable Long idExamen
    ){

        log.info("Endpoint appelé : GET /examen/" + idExamen);

        final Examen entity = examenService.getById(idExamen);
        final ExamenResponse dto = examenMapper.toDto(entity);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Récupérer tout les examen",
            description = "Trouve l'ensemble des  examens")
    public ResponseEntity<List<ExamenResponse>> getExamens(
    ){

        log.info("Endpoint appelé : GET /examen/all");

        final List<Examen> entityList = examenService.getAll();
        final List<ExamenResponse> dtos = examenMapper.toDtoAll(entityList);
        return ResponseEntity.ok(dtos);
    }


    @PutMapping("/add")
    @Operation(
            summary = "Ajouter une experience à un examen",
            description = "On lie un id d'examen et un id "
    )
    public ResponseEntity<ExamenResponse> addExperience(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestBody ExperienceRequestCreate request
    ){

        if(name == null && id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {


            // préparer request body experience
            final Experience entity_exp = experienceMapper.toEntity(request);
            // save expérience
            final Experience entity_save = experienceService.create(entity_exp);
            // créer l'examen avec ajout


            final Examen entity_exa;

            if(id != null){
                entity_exa = examenService.addExp(id, entity_save);
            }
            else{
                entity_exa = examenService.addExp(name, entity_save);
            }
            // creer response
            final ExamenResponse dto = examenMapper.toDto(entity_exa);
            return ResponseEntity.ok().body(dto);
        }
    }




}
