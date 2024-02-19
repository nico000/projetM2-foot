package com.projetM2_foot.controller;


import com.projetM2_foot.api.request.ResultatEssaiRequestCreate;
import com.projetM2_foot.api.response.ResultatExperienceResponse;
import com.projetM2_foot.entity.ResultatDeplacement;
import com.projetM2_foot.entity.ResultatEssai;
import com.projetM2_foot.entity.ResultatExperience;
import com.projetM2_foot.mapper.DeplacementMapper;
import com.projetM2_foot.mapper.ResultatEssaiMapper;
import com.projetM2_foot.mapper.ResultatExperienceMapper;
import com.projetM2_foot.service.EssaiService;
import com.projetM2_foot.service.ResultatExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/essai")
@Slf4j
@RequiredArgsConstructor
@Tag(
        name = "Resultat Essai",
        description = "Gestion des differents résultat des essais"
)
public class EssaiController {



    final DeplacementMapper deplacementMapper;
    final ResultatEssaiMapper resultatEssaiMapper;

    final EssaiService essaiService;

    @PostMapping
    @Operation(
            summary = "Créer un essai",
            description = "Créer un essai et ses déplacements associés")
    public ResponseEntity<Long> getResultatExperienceById(
            @RequestBody ResultatEssaiRequestCreate request
    ){

        log.info("Endpoint appelé : POST /essai/");

        log.info(String.valueOf(request));

        // creer la liste de déplacement
        Set<ResultatDeplacement> rdepSet = new HashSet<>(deplacementMapper.toResEntityAll(request.getDeplacements()));
        // Creer l'essai avec la liste de placement
        ResultatEssai rtry = resultatEssaiMapper.toEntity(request , rdepSet);
        if(rtry == null) return ResponseEntity.notFound().build();

        rtry = essaiService.create(rtry);

        log.info(rtry.getId().toString());
        return ResponseEntity.ok(1L);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Récupérer tout les resultat d'experience",
            description = "Trouve l'ensemble des resultat experience")
    public ResponseEntity<List<ResultatExperienceResponse>> getResultatExperience(
    ){


        return ResponseEntity.ok(null);
    }

}
