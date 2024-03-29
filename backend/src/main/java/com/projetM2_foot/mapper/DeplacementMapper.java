package com.projetM2_foot.mapper;

import com.projetM2_foot.api.request.DeplacementRequestCreate;
import com.projetM2_foot.api.request.ResultatDeplacementRequestCreate;
import com.projetM2_foot.api.response.DeplacementResponse;
import com.projetM2_foot.api.response.ResultatDeplacementResponse;
import com.projetM2_foot.entity.Deplacement;
import com.projetM2_foot.entity.ResultatDeplacement;
import com.projetM2_foot.service.EntiteService;
import com.projetM2_foot.service.EssaiService;
import com.projetM2_foot.service.ScenarioService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class DeplacementMapper {

    final ScenarioService scenarioService;
    final EntiteService entiteService;
    final EssaiService essaiService;


    public Deplacement toEntity (DeplacementRequestCreate request){

        return Deplacement.builder()
                .scenario(scenarioService.getScenario(request.getScenario()))
                .entite(entiteService.getEntite(request.getEntite()))
                .numAction(request.getNumAction())
                .startPosX(request.getStartPosX())
                .startPosY(request.getStartPosY())
                .endPosX(request.getEndPosX())
                .endPosY(request.getEndPosY())
                .build();
    }

    public ResultatDeplacement toResEntity (ResultatDeplacementRequestCreate request){

        return ResultatDeplacement.builder()
                .reussi(false)
                .entite(entiteService.getEntite(request.getEntite()))
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
                .numEntite(entity.getEntite().getNumero())
                .numAction(entity.getNumAction())
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

    public static ResultatDeplacementResponse toResDto(ResultatDeplacement entity){

        return ResultatDeplacementResponse.builder()
                .id(entity.getId())
                .reussi(entity.getReussi())
                .entite(entity.getEntite().getId())
                .numAction(entity.getNumAction())
                .startPosX(entity.getStartPosX())
                .startPosY(entity.getStartPosY())
                .endPosX(entity.getEndPosX())
                .endPosY(entity.getEndPosY())
                .build();
    }

    public List<ResultatDeplacementResponse> toResDtoAll (List<ResultatDeplacement> listEntity){
        return listEntity.stream()
                .map(DeplacementMapper::toResDto)
                .collect(Collectors.toList());
    }

}
