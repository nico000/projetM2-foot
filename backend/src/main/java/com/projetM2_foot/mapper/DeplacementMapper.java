package com.projetM2_foot.mapper;

import com.projetM2_foot.api.request.DeplacementRequestCreate;
import com.projetM2_foot.api.request.ResultatDeplacementRequestCreate;
import com.projetM2_foot.api.response.DeplacementResponse;
import com.projetM2_foot.entity.Deplacement;
import com.projetM2_foot.entity.Entite;
import com.projetM2_foot.entity.ResultatDeplacement;
import com.projetM2_foot.entity.Scenario;
import com.projetM2_foot.service.EntiteService;
import com.projetM2_foot.service.ScenarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class DeplacementMapper {

    final ScenarioService scenarioService;
    final EntiteService entiteService;


    public Deplacement toEntity (DeplacementRequestCreate request){

        final Scenario scenario = scenarioService.getScenario(request.getScenario());
        final Entite entite = entiteService.getEntite(request.getEntite());
        if(scenario == null || entite == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with scenario");

        /*
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id)
         */

        return Deplacement.builder()
                .scenario(scenario)
                .entite(entite)
                .numAction(request.getNumAction())
                .startPosX(request.getStartPosX())
                .startPosY(request.getStartPosY())
                .endPosX(request.getEndPosX())
                .endPosY(request.getEndPosY())
                .build();
    }

    public ResultatDeplacement toResEntity (ResultatDeplacementRequestCreate request){

        final Entite entite = entiteService.getEntite(request.getEntite());
        if(entite == null) return null;

        return ResultatDeplacement.builder()
                .entite(entite)
                .numAction(request.getNumAction())
                .startPosX(request.getStartPosX())
                .startPosY(request.getStartPosY())
                .endPosX(request.getEndPosX())
                .endPosY(request.getEndPosY())
                .build();
    }

    public List<ResultatDeplacement> toResEntityAll (List<ResultatDeplacementRequestCreate> listEntity){
        return listEntity.stream()
                .map(this::toResEntity)
                .collect(Collectors.toList());
    }


    public DeplacementResponse toDto (Deplacement entity){

        return DeplacementResponse.builder()
                .id(entity.getId())
                .scenario(entity.getScenario().getId())
                .entite(entity.getEntite().getId())
                .numAction(entity.getNumAction())
                .numScene(0)
                .numBloc(0)
                .startPosX(entity.getStartPosX())
                .startPosY(entity.getStartPosY())
                .endPosX(entity.getEndPosX())
                .endPosY(entity.getEndPosY())
                .build();
    }

    public List<DeplacementResponse> toDtoAll (List<Deplacement> listEntity){
        return listEntity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
