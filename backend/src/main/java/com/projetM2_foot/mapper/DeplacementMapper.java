package com.projetM2_foot.mapper;

import com.projetM2_foot.api.request.DeplacementRequestCreate;
import com.projetM2_foot.api.response.DeplacementResponse;
import com.projetM2_foot.entity.Deplacement;
import com.projetM2_foot.entity.Entite;
import com.projetM2_foot.entity.Scenario;
import com.projetM2_foot.service.EntiteService;
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


    public Deplacement toEntity (DeplacementRequestCreate request){

        final Scenario scenario = scenarioService.getScenario(request.getScenario());
        final Entite entite = entiteService.getEntite(request.getEntite());

        return Deplacement.builder()
                .scenario(scenario)
                .entite(entite)
                .numAction(request.getNumAction())
                .numScene(request.getNumScene())
                .numBloc(request.getNumBloc())
                .startPosX(request.getStartPosX())
                .startPosY(request.getStartPosY())
                .endPosX(request.getEndPosX())
                .endPosY(request.getEndPosY())
                .build();
    }


    public DeplacementResponse toDto (Deplacement entity){

        return DeplacementResponse.builder()
                .id(entity.getId())
                .scenario(entity.getScenario().getId())
                .entite(entity.getEntite().getId())
                .numAction(entity.getNumAction())
                .numScene(entity.getNumScene())
                .numBloc(entity.getNumBloc())
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
