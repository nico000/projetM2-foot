package com.projetM2_foot.controller;


import com.projetM2_foot.api.request.DeplacementRequestCreate;
import com.projetM2_foot.api.request.ScenarioRequestCreate;
import com.projetM2_foot.api.request.ScenarioRequestUpdate;
import com.projetM2_foot.api.response.DeplacementResponse;
import com.projetM2_foot.api.response.ScenarioResponse;
import com.projetM2_foot.entity.Deplacement;
import com.projetM2_foot.entity.Scenario;
import com.projetM2_foot.mapper.DeplacementMapper;
import com.projetM2_foot.mapper.ScenarioMapper;
import com.projetM2_foot.service.DeplacementService;
import com.projetM2_foot.service.ScenarioService;
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
@RequestMapping("/deplacement")
@Slf4j
@RequiredArgsConstructor
@Tag(
        name = "Deplacement",
        description = "Gestion des déplacements"
)
public class DeplacementController {

    public final DeplacementMapper deplacementMapper;
    public final DeplacementService deplacementService;

    @PostMapping
    @Operation(
            summary = "Créer un déplacement",
            description = "Créer un déplacement à partir d'un scénario et on le lie à une entité")
    public ResponseEntity<DeplacementResponse> createDeplacement (
            @RequestBody DeplacementRequestCreate request){

        log.info("Endpoint appelé : POST /deplacement/");
        final Deplacement entity = deplacementMapper.toEntity(request);
        final Deplacement new_entity = deplacementService.create(entity);
        final DeplacementResponse dto = deplacementMapper.toDto(new_entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


}
