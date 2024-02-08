package com.projetM2_foot.controller;


import com.projetM2_foot.api.request.ExamenRequestCreate;
import com.projetM2_foot.api.response.ExamenResponse;
import com.projetM2_foot.entity.Examen;
import com.projetM2_foot.mapper.ExamenMapper;
import com.projetM2_foot.mapper.ExperienceMapper;
import com.projetM2_foot.repository.ExamenRepository;
import com.projetM2_foot.service.ExamenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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




}
