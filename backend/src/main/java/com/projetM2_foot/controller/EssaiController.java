package com.projetM2_foot.controller;


import com.projetM2_foot.api.request.ResultatEssaiRequestCreate;
import com.projetM2_foot.api.response.FeedbackResponse;
import com.projetM2_foot.entity.ResultatDeplacement;
import com.projetM2_foot.entity.ResultatEssai;
import com.projetM2_foot.mapper.DeplacementMapper;
import com.projetM2_foot.mapper.ResultatEssaiMapper;
import com.projetM2_foot.service.EssaiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

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
    public ResponseEntity<FeedbackResponse> getResultatExperienceById(
            @RequestBody ResultatEssaiRequestCreate request
    ){

        log.info("Endpoint appelé : POST /essai/");

        log.info(String.valueOf(request));

        // creer la liste de déplacement
        Set<ResultatDeplacement> rdepSet = new HashSet<>(deplacementMapper.toResEntityAll(request.getDeplacements()));

        // Vérifie la cohérence entre le résultat et le scénario et retourne le nobre de bonne réponse sur cette essai
        int score = essaiService.getFeedbackListDeplacement(rdepSet , request.getResultatExperience());

        // Creer l'essai avec la liste de placement
        ResultatEssai rtry = resultatEssaiMapper.toEntity(request , rdepSet);
        rtry.setScore(score);
        rtry.setReussi(score == rdepSet.size());
        rtry = essaiService.create(rtry);

        FeedbackResponse dto = resultatEssaiMapper.toFeedbackDto(rtry);
        return ResponseEntity.ok(dto);
    }


}
